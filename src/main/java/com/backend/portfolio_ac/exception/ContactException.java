package com.backend.portfolio_ac.exception;

public class ContactException extends RuntimeException {

    public enum Type {
        NOT_FOUND,
        INVALID_EMAIL,
        MESSAGE_TOO_LONG,
        VALIDATION_ERROR
    }

    private final Type type;

    public ContactException(String message, Type type) {
        super(message);
        this.type = type;
    }

    public Type getType(){
        return type;
    }
}
