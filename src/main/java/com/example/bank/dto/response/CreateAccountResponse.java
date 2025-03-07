package com.example.bank.dto.response;

import java.math.BigDecimal;

public class CreateAccountResponse {
    private Long id;
    private BigDecimal balance;

    public CreateAccountResponse(Long id, BigDecimal balance) {
        this.id = id;
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getBalance() {
        return balance;
    }
}
