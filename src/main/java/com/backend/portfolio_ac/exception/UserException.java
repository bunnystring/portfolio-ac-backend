package com.backend.portfolio_ac.exception;

public class UserException extends RuntimeException{
    public enum Type {
        NOT_FOUND,
        EMAIL_IN_USE,
        INVALID_PASSWORD,
        INVALID_CREDENTIALS,
    }

    private final Type type;

    public UserException(String message, Type type) {
        super(message);
        this.type = type;
    }

    public Type getType() {
        return type;
    }
}
