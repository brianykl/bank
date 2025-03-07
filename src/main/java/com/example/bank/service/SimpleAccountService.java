package com.example.bank.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bank.dto.payload.TransactionDTO;
import com.example.bank.dto.response.CreateAccountResponse;
import com.example.bank.dto.response.GetTransactionHistoryResponse;
import com.example.bank.dto.response.TransferFundsResponse;
import com.example.bank.exception.custom.BusinessException;
import com.example.bank.model.Account;
import com.example.bank.model.Transaction;
import com.example.bank.repository.AccountRepository;


/**
 * implementation of AccountService
 * performs business-logic after recieving request from controller
 * interfaces with repository to store, retrieve, and update data
 * 
 */
@Service
public class SimpleAccountService implements AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public SimpleAccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    /**
     * creates a new account with the given initial balance
     * records the account creation as a transaction
     * 
     * @param initialBalance the starting balance for the new account; should be >= 0.0
     * @return a response containing the new account's id and balance
     */
    @Override
    public CreateAccountResponse createAccount(BigDecimal initialBalance) {
        Account account = accountRepository.create(initialBalance);
        account.addTransaction(new Transaction(null, null, initialBalance, "Account created"));
        return new CreateAccountResponse(account.getId(), account.getBalance());
    }


    /**
     * transfers funds from one account to another
     * validates that the transfer value is greater than 0.00 and that the sender has sufficient funds
     * uses locking mechanism to prevent concurrent modification and deadlocks on accounts
     * 
     * @param senderId the id of the account sending funds
     * @param recipientId the id of the account receiving funds
     * @param value the amount to transfer; must be > 0.0 and less than or equal to the sender's balance
     * @return a response confirming that the transfer was successful
     * @throws BusinessException if the account is not found or not enough funds
     */
    @Override
    public TransferFundsResponse transferFunds(Long senderId, Long recipientId, BigDecimal value) {
        if (value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("Transfer value must be greater than 0.00");
        }
        Account sender = accountRepository.findById(senderId)
            .orElseThrow(() -> new BusinessException( "Sender account not found"));
        
        Account recipient = accountRepository.findById(recipientId)
            .orElseThrow(() -> new BusinessException( "Sender account not found"));

        // note: also considering to encapsulate the lockAccounts and unlockAccounts function calls into template function to promote consistency
        try {
            accountRepository.lockAccounts(senderId, recipientId);
            
            // critical section: check funds and perform transfer
            if (sender.getBalance().compareTo(value) < 0) {
                throw new BusinessException("Insufficient funds for this transfer");
            }

            sender.debit(value);
            recipient.credit(value);
            sender.addTransaction(new Transaction(sender.getId(), recipient.getId(), value.negate(), "Transfer to account " + recipient.getId()));
            recipient.addTransaction(new Transaction(sender.getId(), recipient.getId(), value, "Transfer from account " + recipient.getId()));
        } finally {

            accountRepository.unlockAccounts(senderId, recipientId);
        }

        return new TransferFundsResponse("Transfered successfully");
    }


    /**
     * retrieves the transaction history for the account with the given id
     * maps each transaction to a transaction dto
     * 
     * @param id the id of the account whose transaction history should be retrieved
     * @return a response containing a list of transactions for the account
     * @throws BusinessException if the account is not found
     */
    @Override
    public GetTransactionHistoryResponse getTransactionHistory(Long id) {
        Account account = accountRepository.findById(id)
            .orElseThrow(() -> new BusinessException("Account with not found"));
        
        List<TransactionDTO> transactionDTOs = account.getTransactions().stream()
            .map(tx -> new TransactionDTO(tx.getTimestamp(), tx.getValue(), tx.getDescription()))
            .collect(Collectors.toList());
        
        return new GetTransactionHistoryResponse(transactionDTOs);
    }
    
    
}
