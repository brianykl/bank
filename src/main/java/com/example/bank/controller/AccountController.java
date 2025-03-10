package com.example.bank.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.bank.dto.request.CreateAccountRequest;
import com.example.bank.dto.request.GetTransactionHistoryRequest;
import com.example.bank.dto.request.TransferFundsRequest;
import com.example.bank.dto.response.CreateAccountResponse;
import com.example.bank.dto.response.GetTransactionHistoryResponse;
import com.example.bank.dto.response.TransferFundsResponse;
import com.example.bank.service.AccountService;

/**
 * controller for bank accounts
 * handles http requests for creating accounts, transferring funds, 
 * and retrieving transaction history
 */
@RestController
@RequestMapping("/accounts")
public class AccountController {
    private final AccountService accountService;

    /**
     * creates a new instance of accountcontroller
     *
     * @param accountService the service that handles account business logic
     */
    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * creates a new bank account
     * expects a CreateAccountRequest with an initial balance
     *
     * @param request the CreateAccountRequest containing the initial balance
     * @return a CreateAccountResponse with the new account's id and balance
     */
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public CreateAccountResponse createAccount(@Valid @RequestBody CreateAccountRequest request) {
        return accountService.createAccount(request.getInitialBalance());
    }

    /**
     * transfers funds between accounts
     * expects a TransferFundsRequest with senderid, recipientid, and value
     *
     * @param request the TransferFundsRequestcontaining the sender id, recipient id, and transfer value
     * @return a TransferFundsResponsee indicating the result of the transfer
     */
    @PostMapping("/transfer")
    @ResponseStatus(HttpStatus.OK)
    public TransferFundsResponse transferFunds(@Valid @RequestBody TransferFundsRequest request) {
        return accountService.transferFunds(request.getSenderId(), request.getRecipientId(), request.getValue());
    }

    /**
     * retrieves the transaction history for an account
     * expects a GetTransactionHistoryRequest with the account id
     *
     * @param request the GetTransactionHistoryRequest containing the account id
     * @return a GetTransactionHistoryResponse with a list of transaction details
     */
    @PostMapping("/transactions")
    public GetTransactionHistoryResponse getTransactionHistory(@Valid @RequestBody GetTransactionHistoryRequest request) {
        return accountService.getTransactionHistory(request.getId());
    }
}

