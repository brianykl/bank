package com.example.bank.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public class TransferFundsRequest {
    
    @NotNull(message = "senderId is a required field")
    private Long senderId;

    @NotNull(message = "recipientId is a required field")
    @NotNull
    private Long recipientId;
    
    @NotNull(message = "value is a required field")
    @DecimalMin(value = "0.0", message = "value must be positive")
    private BigDecimal value;

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public Long getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(Long recipientId) {
        this.recipientId = recipientId;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

}
