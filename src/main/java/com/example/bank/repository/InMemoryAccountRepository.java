package com.example.bank.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.example.bank.model.Account;

/**
 * implementation of AccountRepository
 * in-memory storage of accounts, mapping id to account objects
 * provides an implementation for handling concurrency via locks
 * 
 */
@Repository
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
        // sort the ids for consistent locking order
        List<Long> sortedIds = Arrays.stream(ids).sorted().collect(Collectors.toList());
        List<Account> lockedAccounts = new ArrayList<>();

        try {
            for (Long id: sortedIds) {
                Account account = findById(id).orElseThrow(() -> new IllegalArgumentException("Account with id " + id + " not found."));
                boolean acquired = account.getLock().tryLock(5, TimeUnit.SECONDS);
                if (!acquired) {
                    throw new RuntimeException("Could not acquire lock for account " + id);
                }
                lockedAccounts.add(account);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            lockedAccounts.forEach(account -> account.getLock().unlock());
        } catch (Exception e) {
            lockedAccounts.forEach(account -> account.getLock().unlock());
            throw e;
        }    
        
    }

    @Override
    public void unlockAccounts(Long... ids) {
        // sort the ids in reverse for consistent unlocking order
        List<Long> sortedIds = Arrays.stream(ids).sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        for (Long id : sortedIds) {
            findById(id).ifPresent(account -> account.getLock().unlock());
        }

    }

}