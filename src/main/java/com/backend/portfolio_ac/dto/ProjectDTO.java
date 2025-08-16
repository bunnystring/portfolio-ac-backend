package com.backend.portfolio_ac.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

/**
 * Data Transfer Object (DTO) para la entidad de proyecto.
 * Esta clase representa la estructura de datos utilizada para transferir información
 * de un proyecto entre distintas capas de la aplicación, especialmente en las respuestas
 * de la API.
 *
 * @author bunnystring
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProjectDTO {

    /**
     * Nombre del proyecto.
     */
    private String name;

    /**
     * Descripción del proyecto.
     */
    private String description;

    /**
     * Fecha de inicio del proyecto (como cadena de texto).
     */
    private LocalDate start_date;

    /**
     * Fecha de finalización del proyecto.
     */
    private LocalDate end_date;

    /**
     * Indica si el proyecto está activo.
     */
    private String is_active;

    /**
     * URL del repositorio del proyecto.
     */
    private String repository_url;

    /**
     * URL pública relacionada con el proyecto.
     */
    private String url;

    /**
     * Lista de imágenes asociadas al proyecto.
     */
    private List<ProjectImageDTO> ProjectImageDTO;
}