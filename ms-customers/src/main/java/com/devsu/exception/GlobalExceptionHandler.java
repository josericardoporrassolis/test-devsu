package com.devsu.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<?> handleNotFound(DataNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT).build();
    }

    @ExceptionHandler(CustomerException.class)
    public ResponseEntity<?> handleDataIntegrityViolation(CustomerException ex) {
        if (ex.getMessage().equals(CustomerException.CUSTOMER_ALREADY)) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(Map.of("message", "El cliente existe", "id", ex.getExistingId()));
        }
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Internal server error");
    }

}