package com.backend.portfolio_ac.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) para la imagen de un proyecto.
 * Esta clase representa la estructura utilizada para transferir información
 * sobre imágenes asociadas a un proyecto, típicamente como parte de la respuesta
 * de la API.
 *
 * @author bunnystring
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProjectImageDTO {

    /**
     * URL de la imagen asociada al proyecto.
     */
    private String image_url;
}