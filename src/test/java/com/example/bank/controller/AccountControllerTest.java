package com.example.bank.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.bank.dto.request.CreateAccountRequest;
import com.example.bank.dto.request.GetTransactionHistoryRequest;
import com.example.bank.dto.request.TransferFundsRequest;
import com.example.bank.dto.response.CreateAccountResponse;
import com.example.bank.dto.response.GetTransactionHistoryResponse;
import com.example.bank.dto.response.TransferFundsResponse;
import com.example.bank.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;

@WebMvcTest(AccountController.class)
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @MockBean
    private AccountService accountService;

    @Test
    public void testCreateAccount() throws Exception {
        CreateAccountRequest request = new CreateAccountRequest();
        request.setInitialBalance(BigDecimal.valueOf(1000));
        
        CreateAccountResponse response = new CreateAccountResponse(1L, BigDecimal.valueOf(1000));
        when(accountService.createAccount(BigDecimal.valueOf(1000))).thenReturn(response);
        
        mockMvc.perform(post("/accounts/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.balance").value(1000));
    }

    @Test
    public void testTransferFunds() throws Exception {
        TransferFundsRequest request = new TransferFundsRequest();
        request.setSenderId(1L);
        request.setRecipientId(2L);
        request.setValue(BigDecimal.valueOf(200));
        
        TransferFundsResponse response = new TransferFundsResponse("Transferred successfully");
        when(accountService.transferFunds(1L, 2L, BigDecimal.valueOf(200))).thenReturn(response);
        
        mockMvc.perform(post("/accounts/transfer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("Transferred successfully"));
    }

    @Test
    public void testGetTransactionHistory() throws Exception {
        GetTransactionHistoryRequest request = new GetTransactionHistoryRequest();
        request.setId(1L);
        
        GetTransactionHistoryResponse response = new GetTransactionHistoryResponse(List.of());
        when(accountService.getTransactionHistory(1L)).thenReturn(response);
        
        mockMvc.perform(post("/accounts/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.transactions").isArray());
    }
}
