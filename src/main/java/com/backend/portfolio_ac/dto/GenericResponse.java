package com.backend.portfolio_ac.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Respuesta estándar para los endpoints de la API.
 *
 * Incluye únicamente el código de estado HTTP y un mensaje descriptivo.
 * Este DTO puede reutilizarse para respuestas exitosas o de error en toda la aplicación.
 *
 * @author bunnystring
 * @since 2025-07-15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenericResponse {
    private int status;
    private String message;
}
