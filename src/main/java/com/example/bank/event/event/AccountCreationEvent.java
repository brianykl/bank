package com.example.bank.event.event;

import java.math.BigDecimal;

public class AccountCreationEvent {
    private final Long id;
    private final BigDecimal initialBalance;

    public AccountCreationEvent(Long id, BigDecimal initialBalance) {
        this.id = id;
        this.initialBalance = initialBalance;
    }

    public Long getAccountId() {
        return id;
    }

    public BigDecimal getInitialBalance() {
        return initialBalance;
    }
}
