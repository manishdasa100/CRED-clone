package com.cred4.credbackend.dto;

import java.time.LocalDate;

import com.cred4.credbackend.models.TransactionCategory;
import com.cred4.credbackend.models.TransactionType;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDetails {
    
    @JsonProperty(access = Access.READ_ONLY)
    private String transactionId;

    private double amount;

    private String vendor;

    private TransactionType type;

    private TransactionCategory category;

    // @JsonProperty(access = Access.READ_ONLY)
    private String dateOfTransaction;

    private String cardNumber;

}
