package com.example.bank.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * global exception handler for request validation errors
 */
@RestControllerAdvice
public class RequestValidationExceptionHandler {

    /**
     * exception handler to respond to invalid values for fields or missing required fields
     * @param exception the exception thrown when validation fails
     * @return a response entity with a formatted error response and 400 status
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        // collect field error messages, including field name and error message
        List<String> errorMessages = exception.getBindingResult().getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList());
        
        Map<String, Object> errorResponse = Map.of(
                "timestamp", LocalDateTime.now(),
                "status", HttpStatus.BAD_REQUEST.value(),
                "error", "Bad Request",
                "message", "validation failed",
                "errors", errorMessages
        );
        
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * exception handler to respond to unrecognized properties in the request body
     * @param exception the exception thrown when there's an unrecognized field in the request body
     * @return a response entity with a formatted error response and 400 status
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidRequestBody(HttpMessageNotReadableException exception) {
        String message = "invalid request body";
        Throwable cause = exception.getCause();
        if (cause instanceof UnrecognizedPropertyException) {
            UnrecognizedPropertyException upe = (UnrecognizedPropertyException) cause;
            message = upe.getPropertyName() + " is an unrecognized field";
        } else {
            message = "invalid request body: " + exception.getLocalizedMessage();
        }
        
        Map<String, Object> errorResponse = Map.of(
                "timestamp", LocalDateTime.now(),
                "status", HttpStatus.BAD_REQUEST.value(),
                "error", "Bad Request",
                "message", message
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}


