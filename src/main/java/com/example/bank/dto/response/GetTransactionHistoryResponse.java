package com.example.bank.dto.response;

import com.example.bank.dto.payload.TransactionDTO;
import java.util.List;

public class GetTransactionHistoryResponse {
    private List<TransactionDTO> transactions;

    public GetTransactionHistoryResponse(){
    }

    public GetTransactionHistoryResponse(List<TransactionDTO> transactions) {
        this.transactions = transactions;
    }

    public List<TransactionDTO> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionDTO> transactions) {
        this.transactions = transactions;
    }
}

