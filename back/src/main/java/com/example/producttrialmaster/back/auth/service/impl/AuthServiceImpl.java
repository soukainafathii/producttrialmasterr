package com.example.producttrialmaster.back.auth.service.impl;

import com.example.producttrialmaster.back.account.entity.User;
import com.example.producttrialmaster.back.account.repository.UserRepository;
import com.example.producttrialmaster.back.auth.dto.LoginDto;
import com.example.producttrialmaster.back.auth.service.AuthService;
import com.example.producttrialmaster.back.auth.service.TokenStoreService; // Import the TokenStoreService
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenStoreService tokenStoreService;

    private static final String RAW_SECRET_KEY = "VGhpc0lzTXlSYW5kb21KV1RTZWNyZXRLZXkxMjM0NTY3ODkhCg==";
    private static final SecretKey SECRET_KEY = new SecretKeySpec(
            RAW_SECRET_KEY.getBytes(StandardCharsets.UTF_8),
            SignatureAlgorithm.HS256.getJcaName()
    );

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, TokenStoreService tokenStoreService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenStoreService = tokenStoreService;
    }

    @Override
    public String authenticateAndGenerateToken(LoginDto loginDto) {

        User user = userRepository.findByEmail(loginDto.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        String token = generateToken(user.getEmail());

        tokenStoreService.storeToken(user.getEmail(), token);

        return token;
    }

    private String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 2))
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }
}
