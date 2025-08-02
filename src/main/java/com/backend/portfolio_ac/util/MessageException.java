package com.backend.portfolio_ac.util;

public final class MessageException {


    // Constructor privado para evitar la instanciación
    private MessageException() {}

    /**
     * Mensaje cuando el email proporcionado no es válido.
     */
    public static final String EMAIL_INVALID = "The provided email is not valid";

    /**
     * Mensaje cuando el texto es demasiado largo. El límite debe ser concatenado.
     */
    public static final String MESSAGE_TOO_LONG = "The message is too long. Maximum allowed is ";

    /**
     * Mensaje cuando ya hay una solicitud en curso.
     */
    public static final String REQUEST_ALREADY = "There is already a request in progress. Please wait for me to contact you.";

    /**
     * Mensaje cuando el proyecto ya existe.
     */
    public static final String PROJECT_ALREADY = "This project already exists";

    /**
     * Mensaje cuando no se encontraron proyectos asociados.
     */
    public static final String PROJECT_NOT_FOUND = "No associated projects found";

    /**
     * Mensaje cuando un correo electrónico ya está registrado. Concatenar el email antes de este mensaje.
     */
    public static final String EMAIL_ALREADY_REGISTERED = " is already registered";


    public static final String INVALID_CREDENTIALS = "Inválid credentials";

    public static final String USER_NOT_FOUND = "User not found";
}
