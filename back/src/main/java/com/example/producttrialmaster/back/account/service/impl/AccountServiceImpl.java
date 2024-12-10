package com.example.producttrialmaster.back.account.service.impl;

import com.example.producttrialmaster.back.account.dto.AccountDto;
import com.example.producttrialmaster.back.account.entity.User;
import com.example.producttrialmaster.back.account.repository.UserRepository;
import com.example.producttrialmaster.back.account.service.AccountService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AccountServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void createAccount(AccountDto accountDto) {
        if (userRepository.findByEmail(accountDto.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setUsername(accountDto.getUsername());
        user.setFirstname(accountDto.getFirstname());
        user.setEmail(accountDto.getEmail());
        user.setPassword(passwordEncoder.encode(accountDto.getPassword()));

        userRepository.save(user);
    }
}
