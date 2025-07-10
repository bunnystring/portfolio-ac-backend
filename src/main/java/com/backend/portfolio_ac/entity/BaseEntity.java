package com.backend.portfolio_ac.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.UUID;

import java.time.LocalDateTime;

/**
 * Clase base para entidades con auditoria de fechas y control de concurrencia.
 * Proporciona campos para fecha de creación, fehca de ultima moficiación para concurrencia optimista.
 * @author bunnystring
 * @since 2025-07-10
 * @version 1.0
 */
@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity {

    /**
     * Identificador único de la entidad.
     */
    @Id
    @GeneratedValue
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    /**
     * Fecha y hora en que la entidad fue creada.
     */
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    /**
     * Fecha y hora de la última modificación de la entidad.
     */
    @Column(name = "last_modified")
    private LocalDateTime lastModified;

    /**
     * Asigna la fecha actual al crear la entidad
     */
    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.lastModified = now;
    }

    /**
     * Campo de versión para manejo de la concurrencia optimista
     */
    @Version
    private Long version;

    /**
     * actualiza la fecha de ultima modificación antes de actualizar la entidad.
     */
    @PreUpdate
    protected void onUpdate() {
        lastModified = LocalDateTime.now();
    }

}
