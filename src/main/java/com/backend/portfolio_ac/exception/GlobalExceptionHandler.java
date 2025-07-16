package com.backend.portfolio_ac.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserException.class)
    public ResponseEntity<?> handleUserException(UserException ex){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        switch (ex.getType()){
            case NOT_FOUND:
                status = HttpStatus.NOT_FOUND;
                break;
            case EMAIL_IN_USE:
                status = HttpStatus.CONFLICT;
                break;
            case INVALID_PASSWORD:
                status = HttpStatus.BAD_REQUEST;
                break;
            default:
                status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status)
                .body(Map.of(
                        "timestamp", LocalDateTime.now(),
                        "status", status.value(),
                        "error", "User Error",
                        "message", ex.getMessage()
                ));
    }


    // @valid desde el controlador cuando el dto tiene validaciones

    @ExceptionHandler(org.springframework.web.bind.MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException(org.springframework.web.bind.MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of(
                        "timestamp", LocalDateTime.now(),
                        "status", HttpStatus.BAD_REQUEST.value(),
                        "error", "Validation Error",
                        "message", message
                ));
    }


    // contact exception
    @ExceptionHandler(ContactException.class)
    public ResponseEntity<?> handleContactException(ContactException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        switch (ex.getType()){
            case NOT_FOUND:
                status = HttpStatus.NOT_FOUND;
                break;
            case INVALID_EMAIL:
                status = HttpStatus.BAD_REQUEST;
                break;
            case MESSAGE_TOO_LONG:
                status = HttpStatus.BAD_REQUEST;
                break;
            case VALIDATION_ERROR:
                status = HttpStatus.BAD_REQUEST;
                break;
            default:
                status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status)
                .body(Map.of(
                        "timestamp", LocalDateTime.now(),
                        "status", status.value(),
                        "error", "Contact Error",
                        "message", ex.getMessage()
                ));
    }

    // Project exceptions
    @ExceptionHandler(ProjectException.class)
    public ResponseEntity<?> handleContactException(ProjectException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        switch (ex.getType()){
            case NOT_FOUND_PROJECTS:
                status = HttpStatus.NOT_FOUND;
                break;
            case PROJECT_EXIST:
                status = HttpStatus.BAD_REQUEST;
                break;
            case ERROR_DELETE:
                status = HttpStatus.BAD_REQUEST;
                break;
            case ERROR_UPDATE:
                status = HttpStatus.BAD_REQUEST;
                break;
            case ERROR_CREATE:
                status = HttpStatus.BAD_REQUEST;
                break;
            default:
                status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status)
                .body(Map.of(
                        "timestamp", LocalDateTime.now(),
                        "status", status.value(),
                        "error", "Contact Error",
                        "message", ex.getMessage()
                ));
    }


    // Maneja cualquier otra excepcion no controlada
    // Dejar de ultimas
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGenericsException(Exception ex){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of(
                        "timestamp", LocalDateTime.now(),
                        "status", HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "error", "Internal Server Error",
                        "message", ex.getMessage()
                ));
    }
}
