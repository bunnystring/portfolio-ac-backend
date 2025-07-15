package com.backend.portfolio_ac.controller;

import com.backend.portfolio_ac.dto.ContactoRequest;
import com.backend.portfolio_ac.dto.GenericResponse;
import com.backend.portfolio_ac.service.ContactService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.backend.portfolio_ac.util.Messages.CONTACT_SUCCESS;

/**
 * Controlador REST para gestionar los mensajes de contacto del portfolio.
 *
 * Expone el endpoint POST /api/contacto para recepción de mensajes.
 *
 * @author bunnystring
 * @since 2025-07-15
 */
@RestController
@RequestMapping("/contact")
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;

    /**
     * Recibe, valida y persiste un mensaje de contacto.
     *
     * @param request DTO con los datos del mensaje de contacto
     * @return ResponseEntity con repsuesta tipo GenericResponse
     */
    @PostMapping
    public ResponseEntity<GenericResponse> createContact(@Valid @RequestBody ContactoRequest request) {
            contactService.createContact(request);
            GenericResponse response = new GenericResponse(
                    HttpStatus.CREATED.value(),
                    CONTACT_SUCCESS
            );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
