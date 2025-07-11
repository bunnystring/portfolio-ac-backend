package com.backend.portfolio_ac.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para la respuesta de autenticación.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private UserSafeDto user;
    private String token;
}