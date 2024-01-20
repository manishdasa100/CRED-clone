package com.cred4.credbackend.service;

import java.util.List;

import com.cred4.credbackend.dto.CardDetails;
import com.cred4.credbackend.dto.StatementDetails;
import com.cred4.credbackend.dto.TransactionDetails;
import com.cred4.credbackend.exceptions.CardLimitExceededException;
import com.cred4.credbackend.exceptions.InvalidStatementException;
import com.cred4.credbackend.exceptions.NoStatementFoundException;
import com.cred4.credbackend.exceptions.NoSufficientBalanceInCardException;

public interface CardService {
    
    boolean verifyCardNumber(String cardNumber);

    void addCard(String cardNumber);

    CardDetails getCardDetails(String cardNumber);

    void addStatement(StatementDetails statementDetails) throws InvalidStatementException;

    StatementDetails getLatestStatementDetails(String cardNumber) throws NoStatementFoundException;

    StatementDetails getStatementFor(String cardNumber, int month, int year) throws NoStatementFoundException;

    List<TransactionDetails> getTransactionsFor(String statementId);
    
    void updateCurrentBalance(String cardNumber, double updatedCurrentBalance) throws NoSufficientBalanceInCardException, CardLimitExceededException;

    void updateOutstandingAmt(String cardNumber, double currentOutstandingAmt);

    void updateDueDate(String cardNumber, String dueDate);

    void setDueDate(String cardNumber, double outstandingAmount);

    void updateStatementPaymentStatus(String cardNumber, double outstandingAmount) throws NoStatementFoundException;

    String getProvider();
}
