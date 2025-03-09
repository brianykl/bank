package com.example.bank.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.example.bank.exception.custom.RepositoryException;
import com.example.bank.model.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Optional;

/**
 * unit test for InMemoryAccountRepository
 */
public class InMemoryAccountRepositoryTest {

    private InMemoryAccountRepository repository;

    @BeforeEach
    public void setup() {
        repository = new InMemoryAccountRepository();
    }

    @Test
    public void testCreateAndFindById() {
        // create a new account with an initial balance of 1000
        Account account = repository.create(BigDecimal.valueOf(1000));
        assertNotNull(account, "Account should not be null");
        
        // verify the account can be retrieved by its id
        Optional<Account> found = repository.findById(account.getId());
        assertTrue(found.isPresent(), "Account should be found by id");
        assertEquals(BigDecimal.valueOf(1000), found.get().getBalance(), "Balance should be 1000");
    }

    @Test
    public void testListAll() {
        // create two accounts
        repository.create(BigDecimal.valueOf(1000));
        repository.create(BigDecimal.valueOf(500));
        Collection<Account> accounts = repository.listAll();
        assertEquals(2, accounts.size(), "Should list two accounts");
    }

    @Test
    public void testLockAndUnlockAccounts() {
        Account account1 = repository.create(BigDecimal.valueOf(1000));
        Account account2 = repository.create(BigDecimal.valueOf(500));
        
        // attempt to lock both accounts - should not throw an exception
        assertDoesNotThrow(() -> repository.lockAccounts(account1.getId(), account2.getId()),
                "Locking accounts should not throw an exception");

        // now unlock both accounts - should also not throw an exception
        assertDoesNotThrow(() -> repository.unlockAccounts(account1.getId(), account2.getId()),
                "Unlocking accounts should not throw an exception");
    }

    @Test
    public void testLockAccountsWithInvalidId() {
        // attempt to lock an account with an id that doesn't exist
        Exception exception = assertThrows(RepositoryException.class, () -> repository.lockAccounts(999L));
        assertTrue(exception.getMessage().contains("not found"),
                "Should throw an exception indicating the account was not found");
    }
}
