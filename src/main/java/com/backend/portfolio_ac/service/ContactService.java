package com.backend.portfolio_ac.service;

import com.backend.portfolio_ac.dto.ContactoRequest;
import com.backend.portfolio_ac.entity.Contacto;

/**
 * Servicio para gestionar mensajes de contacto en el portfolio.
 *
 * @author bunnystring
 * @since 2025-07-15
 */
public interface ContactService {
    /**
     * Procesa y persiste un nuevo mensaje de contacto.
     *
     * @param request DTO con los datos del mensaje de contacto
     * @return la entidad Contacto persistida
     */
    Contacto createContact(ContactoRequest request);
}