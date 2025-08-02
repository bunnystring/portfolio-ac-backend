package com.backend.portfolio_ac.service;

public interface EmailService {
    void sendVerificationEmail(String toEmail, String token);
}
