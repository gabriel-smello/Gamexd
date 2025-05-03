package com.gamexd.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFound(EntityNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(buildResponse(HttpStatus.NOT_FOUND, e.getMessage()));
    }

    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<?> handleSecurity(SecurityException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(buildResponse(HttpStatus.FORBIDDEN, e.getMessage()));
    }

    // Outros handlers se necessário...

    private Map<String, Object> buildResponse(HttpStatus status, String message) {
        return Map.of(
                "timestamp", LocalDateTime.now(),
                "status", status.value(),
                "error", status.getReasonPhrase(),
                "message", message
        );
    }
}
