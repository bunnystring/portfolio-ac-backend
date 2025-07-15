package com.backend.portfolio_ac.service.impl;


import com.backend.portfolio_ac.dto.ContactoRequest;
import com.backend.portfolio_ac.entity.Contacto;
import com.backend.portfolio_ac.exception.ContactException;
import com.backend.portfolio_ac.repository.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.backend.portfolio_ac.service.ContactService;
import org.apache.commons.validator.routines.EmailValidator;
import static com.backend.portfolio_ac.util.Values.MAX_MESSAGE_LENGTH;

/**
 * Implementación del servicio para gestionar mensajes de contacto en el portfolio.
 *
 * @author bunnystring
 * @since 2025-07-15
 */
@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;

    /**
     * Procesa y persiste un nuevo mensaje de contacto.
     *
     * @param request DTO con los datos del mensaje de contacto
     * @return la entidad Contacto persistida
     */
    @Override
    public Contacto createContact(ContactoRequest request){

        if (!isValidEmail(request.getEmail())) {
            throw new ContactException("El email proporcionado no es válido", ContactException.Type.INVALID_EMAIL);
        }

        if (request.getMensaje() != null && request.getMensaje().length() > MAX_MESSAGE_LENGTH){
            throw new ContactException("El mensaje es demasiado largo. Máximo permitido: " + MAX_MESSAGE_LENGTH
                    + " caracteres.", ContactException.Type.MESSAGE_TOO_LONG);
        }

        contactRepository.findByEmail(request.getEmail()).ifPresent(c -> {
            throw new ContactException("Ya hay una solicitud en curso,espera a que me contacte contigo", ContactException.Type.VALIDATION_ERROR);
        });

        Contacto contacto = new Contacto();
        contacto.setNombre(request.getNombre());
        contacto.setEmail(request.getEmail());
        contacto.setAsunto(request.getAsunto());
        contacto.setMensaje(request.getMensaje());
        contacto.setTelefono(request.getTelefono());

        return contactRepository.save(contacto);
    }

    // Validacion robusta email con apache commons
    private boolean isValidEmail(String email) {
        return EmailValidator.getInstance().isValid(email);
    }
}
