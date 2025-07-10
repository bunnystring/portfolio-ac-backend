package com.backend.portfolio_ac.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * Entidad que representa un mensaje de contacto dejado por un usuario en el portfolio web.
 *
 * Hereda el identificador UUID y campos de auditoría de {@link BaseEntity}.
 *
 * @author bunnystring
 * @since 2025-07-10
 * @version 1.1
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "contactos")
public class Contacto extends BaseEntity{

    /**
     * Nombre de la persona que deja el mensaje.
     */
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100)
    private String nombre;

    /**
     * Correo electrónico del remitente.
     */
    @NotBlank(message = "El correo electrónico es obligatorio")
    @Email(message = "El correo electrónico debe ser válido")
    @Size(max = 180)
    private String email;

    /**
     * Teléfono de contacto del remitente.
     */
    @Size(max = 20)
    private String telefono;

    /**
     * Asunto del mensaje de contacto.
     */
    @NotBlank(message = "El asunto es obligatorio")
    @Size(max = 150)
    private String asunto;

    /**
     * Cuerpo del mensaje.
     */
    @NotBlank(message = "El mensaje no puede estar vacío")
    @Column(length = 2000)
    private String mensaje;
}
