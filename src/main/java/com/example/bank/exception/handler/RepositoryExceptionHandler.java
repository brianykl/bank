package com.example.bank.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.example.bank.exception.custom.RepositoryException;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * global exception handler for repository exceptions
 */
@RestControllerAdvice
public class RepositoryExceptionHandler {

    /**
     * handles RepositoryException by returning a bad request response
     *
     * @param exception the repository exception thrown from the repository layer
     * @return a response entity with a formatted error response and 400 status
     */
    @ExceptionHandler(RepositoryException.class)
    public ResponseEntity<Map<String, Object>> handleRepositoryException(RepositoryException exception) {
        Map<String, Object> errorResponse = Map.of(
                "timestamp", LocalDateTime.now(),
                "status", HttpStatus.BAD_REQUEST.value(),
                "error", "Bad Request",
                "message", exception.getMessage()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}