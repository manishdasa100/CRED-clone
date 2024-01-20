package com.cred4.credbackend.models;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionByCategory {
    
    @Id
    private String category;

    private double totalTransactionAmount;
}