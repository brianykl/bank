package com.example.bank.event.event;

import java.math.BigDecimal;

public class TransferFundsEvent {
    private final Long senderId;
    private final Long recipientId;
    private final BigDecimal value;

    public TransferFundsEvent(Long senderId, Long recipientId, BigDecimal value) {
        this.senderId = senderId;
        this.recipientId = recipientId;
        this.value = value;
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
}
