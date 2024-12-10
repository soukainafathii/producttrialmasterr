package com.example.producttrialmaster.back.auth.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class TokenStoreService {

    private final ConcurrentHashMap<String, String> tokenStore = new ConcurrentHashMap<>();

    public void storeToken(String email, String token) {
        tokenStore.put(email, token);
    }

    public String getToken(String email) {
        return tokenStore.get(email);
    }
}
