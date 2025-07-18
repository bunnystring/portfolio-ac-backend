package com.backend.portfolio_ac.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "projects")
@Builder
public class Project extends BaseEntity{

    /**
     * Nombre del proyecto
     */
    @NotBlank(message = "El nombre del proyecto es obligatorio")
    @Size(max = 100)
    private String name;

    /**
     * Descripción del proyecto.
     */
    @Column(length = 2000)
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
     * Lista de las imagenes asociadas al proyecto.
     */

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ProjectImage> images = new ArrayList<>();

}
