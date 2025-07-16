package com.backend.portfolio_ac.repository;

import com.backend.portfolio_ac.entity.Contacto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repositorio JPA para la entidad Contacto.
 *
 * Proporciona operaciones CRUD sobre la tabla de contactos.
 *
 * @author bunnystring
 * @since 2025-07-15
 */
@Repository
public interface ContactRepository extends JpaRepository<Contacto, UUID> {

    Optional<Contacto> findByEmail(String email);
}
