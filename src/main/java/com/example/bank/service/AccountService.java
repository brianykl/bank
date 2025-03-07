package com.example.bank.service;

import java.math.BigDecimal;

import com.example.bank.dto.response.CreateAccountResponse;
import com.example.bank.dto.response.GetTransactionHistoryResponse;
import com.example.bank.dto.response.TransferFundsResponse;

public interface AccountService {
    CreateAccountResponse createAccount(BigDecimal initialBalance);
    TransferFundsResponse transferFunds(Long senderId, Long recipientId, BigDecimal value);
    GetTransactionHistoryResponse getTransactionHistory(Long id);
}
