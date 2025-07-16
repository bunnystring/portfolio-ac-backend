package com.backend.portfolio_ac.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

/**
 * Entidad que representa una imagen asociada a un proyecto.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "project_images")
@Builder
public class ProjectImage extends BaseEntity{

    /**
     * Url de la imagen
     */
    private String imageUrl;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    @JsonBackReference
    private Project project;
}
