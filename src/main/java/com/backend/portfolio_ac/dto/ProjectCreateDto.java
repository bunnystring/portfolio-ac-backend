package com.backend.portfolio_ac.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ProjectCreateDto {

    /**
     * Nombre del proyecto
     */
    @NotBlank(message = "El nombre del proyecto es obligatorio")
    @Size(max = 100)
    private String name;

    /**
     * Descripción del proyecto.
     */
    @Size(max = 2000)
    private String description;

    /**
     * URL pública del proyecto.
     */
    private String url;

    /**
     * URL del repositorio del proyecto.
     */
    private String repositoryUrl;

    /**
     * Fecha de inicio del proyecto.
     */
    private LocalDate startDate;

    /**
     * Fecha de finalización del proyecto.
     */
    private LocalDate endDate;

    /**
     * Indica si el proyecto está activo.
     */
    private boolean isActive = true;

    /**
     * Lista de URLs de las imágenes asociadas al proyecto.
     * Puedes cambiar el tipo si necesitas DTOs de imagen más complejos.
     */
    private List<String> images;
}
