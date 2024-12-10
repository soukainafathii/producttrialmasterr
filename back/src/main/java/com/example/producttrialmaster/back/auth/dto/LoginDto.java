package com.example.producttrialmaster.back.auth.dto;

import lombok.Data;

@Data
public class LoginDto {
    private String email;
    private String password;
}
