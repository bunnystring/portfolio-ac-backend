package com.backend.portfolio_ac.service;

import com.backend.portfolio_ac.dto.RegisterRequest;
import org.springframework.http.ResponseEntity;

public interface VerificationService {
    ResponseEntity<String> verifyEmail(String code);

    void sendVerificationEmail(RegisterRequest request, String accessToken);

    String generateVerificationCode(String email);
}
