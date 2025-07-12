package com.backend.portfolio_ac.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * DTO para la solicitud de registro de usuario.
 * Contiene los campos necesarios para el registro.
 *
 * @author bunnystring
 */
@Data
public class RegisterRequest {

    @NotBlank(message = "Name is required")
    @Size(max = 50)
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be validated")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "The password must be at least 6 characters long")
    private String password;
}