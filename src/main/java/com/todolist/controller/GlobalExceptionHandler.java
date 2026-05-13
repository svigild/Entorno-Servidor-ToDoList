package com.todolist.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.Map;

// Manejador global de excepciones para devolver errores en formato JSON
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Captura excepciones de logica de negocio (ej: recurso no encontrado)
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleRuntimeException(RuntimeException ex) {
        Map<String, Object> body = Map.of(
                "status", 400,
                "error", "Peticion incorrecta",
                "message", ex.getMessage()
        );
        return ResponseEntity.badRequest().body(body);
    }

    // Captura errores de validacion de los DTOs (@NotBlank, @Email, etc.)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .reduce((a, b) -> a + "; " + b)
                .orElse("Error de validacion");

        Map<String, Object> body = Map.of(
                "status", 400,
                "error", "Error de validacion",
                "message", message
        );
        return ResponseEntity.badRequest().body(body);
    }
}
