package com.example.bank.repository;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Optional;

import com.example.bank.model.Account;

public interface AccountRepository {
    Account create(BigDecimal initialBalance);
    Optional<Account> findById(Long id);
    Collection<Account> listAll();
    void lockAccounts(Long... ids);
    void unlockAccounts(Long... ids);
}
