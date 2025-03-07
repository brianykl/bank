package com.example.bank.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * object representation of bank account
 */
public class Account {
    private final Long id;
    private BigDecimal balance;
    private final List<Transaction> transactions;
    private final Lock lock;
    
    public Account(Long id, BigDecimal initialBalance) {
        this.id = id;
        this.balance = initialBalance;
        this.transactions = new ArrayList<>();
        this.lock = new ReentrantLock();
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void debit(BigDecimal value) {
        balance = balance.subtract(value);
    }

    public void credit(BigDecimal value) {
        balance = balance.add(value);
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public Lock getLock() {
        return lock;
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }
}
