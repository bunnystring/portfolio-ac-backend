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

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 50)
    private String name;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email debe ser valido")
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String password;
}