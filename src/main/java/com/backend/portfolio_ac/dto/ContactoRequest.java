package com.backend.portfolio_ac.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO para recibir y validar solicitudes de contacto.
 *
 * @author bunnystring
 * @since 2025-07-15
 */
@Getter
@Setter
public class ContactoRequest {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100)
    private String nombre;

    @NotBlank(message = "El correo electrónico es obligatorio")
    @Email(message = "El correo electrónico debe ser válido")
    @Size(max = 180)
    private String email;

    @Size(max = 20)
    private String telefono;

    @NotBlank(message = "El asunto es obligatorio")
    @Size(max = 150)
    private String asunto;

    @NotBlank(message = "El mensaje no puede estar vacio")
    @Size(max = 2000)
    private String mensaje;

}
