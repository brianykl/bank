package com.example.bank.event.listener;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.example.bank.event.event.AccountCreationEvent;
import com.example.bank.event.event.TransferFundsEvent;


/**
 * event listener to centrally log changes in bank accounts
 * 
 */
@Component
public class DomainEventListener {

    @EventListener
    public void handleAccountCreated(AccountCreationEvent event) {

        System.out.println("Account created: id=" + event.getAccountId() +
                           ", initial balance=" + event.getInitialBalance());
    }

    @EventListener
    public void handleTransferFunds(TransferFundsEvent event) {
        System.out.println("Transfer executed: senderId=" + event.getSenderId() +
                           ", recipientId=" + event.getRecipientId() +
                           ", value=" + event.getValue());
    }
}