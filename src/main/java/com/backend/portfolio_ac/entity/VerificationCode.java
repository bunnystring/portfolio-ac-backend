package com.backend.portfolio_ac.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class VerificationCode extends BaseEntity {

    @Column(nullable = false)
    private String email; // Email del usuario (puede ser redundante si tienes User, pero útil si permites usuarios sin registro)

    @Column(nullable = false)
    private String code; // El código de verificación

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user; // Opcional: relación con User si ya existe

    @Column(nullable = false)
    private LocalDateTime generatedAt; // Cuándo se generó el código

    @Column(nullable = false)
    private LocalDateTime expiresAt; // Fecha de expiración

    @Column(nullable = false)
    private boolean verified = false; // Si ya fue verificado

    private LocalDateTime verifiedAt; // Cuándo se verificó (opcional)

    private int attemptCount = 0; // Para limitar intentos (opcional)
}
