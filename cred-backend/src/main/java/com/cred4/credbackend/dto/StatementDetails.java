package com.cred4.credbackend.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatementDetails {
    
    @JsonProperty(access = Access.READ_ONLY)
    private String statementId;

    private String billStartDate;

    private String billEndDate;

    private String dueDate;

    private String cardNumber;

    @JsonProperty(access = Access.READ_ONLY)
    private double cardLimit;

    @JsonProperty(access = Access.READ_ONLY)
    private double outstandingAmount;

    @JsonProperty(access = Access.READ_ONLY)
    private boolean paymentStatus;

    @JsonProperty(access = Access.READ_ONLY)
    private SmartStatementDetails smartStatementDetails;

}
