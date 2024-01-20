package com.cred4.credbackend.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "PaymentRRecord")
public class PaymentRecord {
    
    @Id
    private String id;

    private String statementId;

    private double amountPaid;

    private double outstandingAmount;
}
