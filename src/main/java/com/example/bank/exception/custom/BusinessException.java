package com.example.bank.exception.custom;

/**
 * a custom exception for business rule violations
 */
public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}