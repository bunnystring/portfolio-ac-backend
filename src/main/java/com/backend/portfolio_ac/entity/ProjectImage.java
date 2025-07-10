package com.backend.portfolio_ac.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entidad que representa una imagen asociada a un proyecto.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "project_images")

public class ProjectImage extends BaseEntity{

    /**
     * Url de la imagen
     */
    private String imageUrl;


    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
}
