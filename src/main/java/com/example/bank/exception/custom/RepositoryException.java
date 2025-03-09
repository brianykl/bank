package com.example.bank.exception.custom;


/**
 * a custom exception to flag exceptions at the repository layer
 */
public class RepositoryException extends RuntimeException{
    public RepositoryException(String message) {
        super(message);
    }
    
    public RepositoryException(String message, Throwable cause) {
        super(message, cause);
    }
}
