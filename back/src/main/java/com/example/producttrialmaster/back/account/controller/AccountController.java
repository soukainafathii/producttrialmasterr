package com.example.producttrialmaster.back.account.controller;

import com.example.producttrialmaster.back.account.dto.AccountDto;
import com.example.producttrialmaster.back.account.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<String> createAccount(@RequestBody AccountDto accountDto) {
        try {
            accountService.createAccount(accountDto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Account created successfully.");
        } catch (RuntimeException ex) {
            if (ex.getMessage().equalsIgnoreCase("Email already exists")) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }
}
