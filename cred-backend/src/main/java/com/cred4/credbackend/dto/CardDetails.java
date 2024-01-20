package com.cred4.credbackend.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardDetails {
    
    private String cardNumber;

    private LocalDate expiryDate; 

    private String nameOnCard;

    private double currentBalance;

    private double cardLimit;

    private double outstandingAmt;

    private String dueDate;

    private String cardProvider;
}
