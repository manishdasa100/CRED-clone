package com.cred4.credbackend.repository;

import java.util.Optional;

import com.cred4.credbackend.dto.StatementDetails;
import com.cred4.credbackend.exceptions.InvalidStatementException;

public interface StatementRepositoryService {
    
    Optional<StatementDetails> getLatestStatementForCard(String cardNumber);

    Optional<StatementDetails> getStatementFor(String cardNumber, int month, int year);

    Optional<StatementDetails> getStatement(String statementId);

    void addStatement(StatementDetails statementDetails) throws InvalidStatementException;

    void removeStatement(String statementId);

    void setStatementPaymentStatus(String statementId, boolean paymentStatus);

}
