package com.cred4.credbackend.models;

import java.time.LocalDate;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "CreditCard")
public class CreditCard {

    @Id
    private String id;
    
    @Indexed(unique = true)
    private String cardNumber;

    private LocalDate expiryDate; 

    private String nameOnCard;

    private double currentBalance;

    private double cardLimit;

    private double outstandingAmt;

    private String dueDate;

    private String cardProvider;
}
