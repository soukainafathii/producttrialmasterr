package com.example.producttrialmaster.back.auth.controller;


import com.example.producttrialmaster.back.auth.dto.LoginDto;
import com.example.producttrialmaster.back.auth.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/token")
    public ResponseEntity<String> generateToken(@RequestBody LoginDto loginDto) {
        String token = authService.authenticateAndGenerateToken(loginDto);
        return ResponseEntity.ok(token);
    }
}