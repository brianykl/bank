package com.example.bank.dto.response;

public class TransferFundsResponse {
    private String message;

    public TransferFundsResponse() {
    }

    public TransferFundsResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
