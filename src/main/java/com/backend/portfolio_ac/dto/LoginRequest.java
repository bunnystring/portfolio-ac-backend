package com.backend.portfolio_ac.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * DTO para la solicitud de inicio de sesión.
 * Contiene el email y la contraseña del usuario.
 * @author bunnystring
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    private String email;
    private String password;
}