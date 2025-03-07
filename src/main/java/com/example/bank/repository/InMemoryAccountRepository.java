package com.example.bank.repository;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import com.example.bank.model.Account;

/**
 * implementation of AccountRepository
 * in-memory storage of accounts, mapping id to account objects
 * provides an implementation for handling concurrency via locks
 * 
 */
public class InMemoryAccountRepository implements AccountRepository {
    private final ConcurrentHashMap<Long, Account> accounts = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(0);
    
    @Override
    public Account create(BigDecimal initialBalance) {
        Long id = idGenerator.getAndIncrement();
        Account account = new Account(id, initialBalance);
        accounts.put(id, account);
        return account;
    }

    @Override
    public Optional<Account> findById(Long id) {
        return Optional.ofNullable(accounts.get(id));
    }

    @Override
    public Collection<Account> listAll() {
        return accounts.values();
    }

    @Override
    public void lockAccounts(Long... ids) {
    
    }

    @Override
    public void unlockAccounts(Long... ids) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'unlockAccounts'");
    }

}