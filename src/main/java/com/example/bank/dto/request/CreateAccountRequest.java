package com.example.bank.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public class CreateAccountRequest {
    @NotNull(message = "initialBalance is a required field")
    @DecimalMin(value = "0.0", message = "initialBalance must be non-negative")
    private BigDecimal initialBalance;

    public BigDecimal getInitialBalance() {
        return initialBalance;
    }

    public void setInitialBalance(BigDecimal initialBalance) {
        this.initialBalance = initialBalance;
    }
}