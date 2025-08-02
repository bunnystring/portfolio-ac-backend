package com.backend.portfolio_ac.service.impl;


import com.backend.portfolio_ac.dto.ContactoRequest;
import com.backend.portfolio_ac.entity.Contacto;
import com.backend.portfolio_ac.exception.ContactException;
import com.backend.portfolio_ac.repository.ContactRepository;
import com.backend.portfolio_ac.util.MessageException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@RequiredArgsConstructor
@Service
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
            log.error(MessageException.EMAIL_INVALID);
            throw new ContactException(MessageException.EMAIL_INVALID, ContactException.Type.INVALID_EMAIL);
        }

        if (request.getMensaje() != null && request.getMensaje().length() > MAX_MESSAGE_LENGTH){
            log.error(MessageException.MESSAGE_TOO_LONG);
            throw new ContactException(MessageException.MESSAGE_TOO_LONG + MAX_MESSAGE_LENGTH
                    + " caracteres.", ContactException.Type.MESSAGE_TOO_LONG);
        }

        contactRepository.findByEmail(request.getEmail()).ifPresent(c -> {
            log.error(MessageException.REQUEST_ALREADY);
            throw new ContactException(MessageException.REQUEST_ALREADY, ContactException.Type.VALIDATION_ERROR);
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
