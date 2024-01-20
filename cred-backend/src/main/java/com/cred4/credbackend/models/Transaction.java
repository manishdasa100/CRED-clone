package com.cred4.credbackend.models;

import java.time.LocalDate;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Transaction")
public class Transaction {
    
    @Id
    private String transactionId;

    private double amount;

    private String vendor;

    private TransactionType type;

    private TransactionCategory category;

    private String dateOfTransaction;

    private String cardNumber;

}
