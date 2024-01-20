package com.cred4.credbackend.service;

import com.cred4.credbackend.dto.CardDetails;
import com.cred4.credbackend.dto.SmartStatementDetails;
import com.cred4.credbackend.dto.StatementDetails;
import com.cred4.credbackend.exceptions.InvalidStatementException;
import com.cred4.credbackend.exceptions.NoStatementFoundException;

public interface StatementService {
    
    StatementDetails getLatestStatementDetails(String cardNumber) throws NoStatementFoundException;

    StatementDetails getStatementFor(String cardNumber, int month, int year) throws NoStatementFoundException;

    StatementDetails getStatement(String statementId) throws NoStatementFoundException;

    double generateFullStatement(CardDetails cardDetails, StatementDetails statementDetails) throws InvalidStatementException;

    SmartStatementDetails getSmartStatement(StatementDetails statementDetails);

    void setPaymentStatus(String statementId, boolean paymentStatus);

}
