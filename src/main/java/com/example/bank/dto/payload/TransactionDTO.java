package com.example.bank.dto.payload;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionDTO {
    private LocalDateTime timestamp;
    private BigDecimal value;
    private String description;

    public TransactionDTO() {
    }

    public TransactionDTO(LocalDateTime timestamp, BigDecimal value, String description) {
        this.timestamp = timestamp;
        this.value = value;
        this.description = description;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}