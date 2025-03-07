package com.example.bank.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * object representation of transaction
 */
public class Transaction {
    private final LocalDateTime timestamp;
    private final Long senderId;
    private final Long recipientId;
    private final BigDecimal value;
    private final String description;

    public Transaction(Long senderId, Long recipientId, BigDecimal value, String description) {
        this.timestamp = LocalDateTime.now();
        this.senderId = senderId;
        this.recipientId = recipientId;
        this.value = value;
        this.description = description;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public Long getSenderId() {
        return senderId;
    }

    public Long getRecipientId() {
        return recipientId;
    }

    public BigDecimal getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
}

