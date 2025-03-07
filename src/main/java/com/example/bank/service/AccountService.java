package com.example.bank.service;

import java.math.BigDecimal;

import com.example.bank.dto.CreateAccountResponse;
import com.example.bank.dto.GetTransactionHistoryResponse;
import com.example.bank.dto.TransferFundsResponse;

public interface AccountService {
    CreateAccountResponse createAccount(BigDecimal initialBalance);
    TransferFundsResponse transferFunds(Long senderId, Long recipientId, BigDecimal value);
    GetTransactionHistoryResponse getTransactionHistory(Long id);
}
