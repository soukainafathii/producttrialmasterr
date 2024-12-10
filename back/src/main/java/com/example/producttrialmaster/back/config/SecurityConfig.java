package com.example.producttrialmaster.back.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.example.producttrialmaster.back.auth.service.TokenStoreService;
import org.springframework.web.cors.CorsConfiguration;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

@Configuration
public class SecurityConfig {

    // NOTE: I hardcoded the secret key for demonstration purposes only.
    // In a real production environment, this key should be stored securely.
    private static final String RAW_SECRET_KEY = "VGhpc0lzTXlSYW5kb21KV1RTZWNyZXRLZXkxMjM0NTY3ODkhCg==";
    private static final SecretKey SECRET_KEY = new SecretKeySpec(
            RAW_SECRET_KEY.getBytes(StandardCharsets.UTF_8),
            "HmacSHA256"
    );

    private final TokenStoreService tokenStoreService;

    public SecurityConfig(TokenStoreService tokenStoreService) {
        this.tokenStoreService = tokenStoreService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.addAllowedOrigin("http://localhost:4200");
                    config.addAllowedHeader("*");
                    config.addAllowedMethod("*");
                    config.setAllowCredentials(true);
                    return config;
                }))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, "/api/products", "/api/products/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/products", "/api/products/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/products", "/api/products/**").authenticated()
                        .requestMatchers(HttpMethod.PATCH, "/api/products", "/api/products/**").authenticated()
                        .requestMatchers("/api/token").permitAll()
                        .requestMatchers("/api/account").permitAll()
                        .requestMatchers("/api/cart", "/api/cart/**").authenticated()
                        .requestMatchers("/api/wishlist", "/api/wishlist/**").authenticated()
                        .anyRequest().authenticated()
                )
                .addFilterBefore((servletRequest, servletResponse, chain) -> {
                    HttpServletRequest request = (HttpServletRequest) servletRequest;
                    HttpServletResponse response = (HttpServletResponse) servletResponse;

                    String authHeader = request.getHeader("Authorization");

                    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                        String email = (String) request.getAttribute("user");
                        if (email != null) {
                            String token = tokenStoreService.getToken(email);
                            if (token != null) {
                                authHeader = "Bearer " + token;
                                request.setAttribute("Authorization", authHeader);
                            }
                        }
                    }

                    if (authHeader != null && authHeader.startsWith("Bearer ")) {
                        String token = authHeader.substring(7);
                        try {
                            Claims claims = Jwts.parserBuilder()
                                    .setSigningKey(SECRET_KEY)
                                    .build()
                                    .parseClaimsJws(token)
                                    .getBody();

                            String email = claims.getSubject();
                            System.out.println("Email from token: " + email);

                            UsernamePasswordAuthenticationToken authentication =
                                    new UsernamePasswordAuthenticationToken(email, null, null);
                            SecurityContextHolder.getContext().setAuthentication(authentication);

                            request.setAttribute("user", email);

                            boolean isAdmin = "admin@admin.com".equals(email);
                            request.setAttribute("isAdmin", isAdmin);

                            String method = request.getMethod();
                            if (request.getRequestURI().startsWith("/api/products") && !isAdmin && (method.equals("POST") || method.equals("PATCH") || method.equals("DELETE"))) {
                                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                                response.getWriter().write("Only admin@admin.com can perform this action.");
                                return;
                            }

                        } catch (Exception e) {
                            System.out.println("Token validation failed: " + e.getMessage());
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.getWriter().write("Invalid or expired token.");
                            return;
                        }
                    }
                    chain.doFilter(servletRequest, servletResponse);
                }, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
