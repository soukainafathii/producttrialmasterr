package com.example.producttrialmaster.back.auth.service;

import com.example.producttrialmaster.back.auth.dto.LoginDto;

public interface AuthService {

    String authenticateAndGenerateToken(LoginDto loginDto);
}
