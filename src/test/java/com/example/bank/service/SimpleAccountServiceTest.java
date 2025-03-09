package com.example.bank.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.bank.dto.response.*;
import com.example.bank.exception.custom.BusinessException;
import com.example.bank.model.Account;
import com.example.bank.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationEventPublisher;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * unit test for SimpleAccountService
 */
public class SimpleAccountServiceTest {

    private AccountRepository accountRepository;
    private ApplicationEventPublisher eventPublisher;
    private SimpleAccountService accountService;

    @BeforeEach
    public void setup() {
        accountRepository = mock(AccountRepository.class);
        eventPublisher = mock(ApplicationEventPublisher.class);
        accountService = new SimpleAccountService(accountRepository, eventPublisher);
    }

    @Test
    public void testCreateAccount() {
        Account account = new Account(1L, BigDecimal.valueOf(1000));
        when(accountRepository.create(BigDecimal.valueOf(1000))).thenReturn(account);

        CreateAccountResponse response = accountService.createAccount(BigDecimal.valueOf(1000));
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals(BigDecimal.valueOf(1000), response.getBalance());
    }

    @Test
    public void testTransferFundsInsufficientFunds() {
        Account sender = new Account(1L, BigDecimal.valueOf(500));
        Account recipient = new Account(2L, BigDecimal.valueOf(1000));

        when(accountRepository.findById(1L)).thenReturn(Optional.of(sender));
        when(accountRepository.findById(2L)).thenReturn(Optional.of(recipient));

        BusinessException exception = assertThrows(BusinessException.class, () ->
            accountService.transferFunds(1L, 2L, BigDecimal.valueOf(600))
        );
        assertTrue(exception.getMessage().contains("Insufficient funds"));
    }

}
