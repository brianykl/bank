package com.example.bank.dto.request;

import jakarta.validation.constraints.NotNull;

public class GetTransactionHistoryRequest {
    @NotNull(message = "id is a required field")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
