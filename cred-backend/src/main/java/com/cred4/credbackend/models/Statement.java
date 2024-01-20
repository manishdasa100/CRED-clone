package com.cred4.credbackend.models;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Statement")
public class Statement {
    
    @Id
    private String statementId;

    // @Indexed(unique = true)
    private String billStartDate;

    // @Indexed(unique = true)
    private String billEndDate;

    // @Indexed(unique = true)
    private String dueDate;

    private String cardNumber;

    private double cardLimit;

    private double outstandingAmount;

    private boolean paymentStatus;
}
