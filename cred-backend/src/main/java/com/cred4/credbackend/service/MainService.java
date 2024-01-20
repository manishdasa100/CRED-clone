package com.cred4.credbackend.service;

import java.util.Date;
import java.util.List;

import com.cred4.credbackend.dto.CardDetails;
import com.cred4.credbackend.dto.PaymentRecordDetails;
import com.cred4.credbackend.dto.StatementDetails;
import com.cred4.credbackend.dto.TransactionDetails;
import com.cred4.credbackend.exceptions.CardLimitExceededException;
import com.cred4.credbackend.exceptions.DuplicateCardException;
import com.cred4.credbackend.exceptions.InvalidCardException;
import com.cred4.credbackend.exceptions.InvalidStatementException;
import com.cred4.credbackend.exceptions.NoCardFoundException;
import com.cred4.credbackend.exceptions.NoStatementFoundException;
import com.cred4.credbackend.exceptions.NoSufficientBalanceInCardException;
import com.cred4.credbackend.models.PaymentRecord;
import com.cred4.credbackend.models.Statement;
import com.cred4.credbackend.models.Transaction;

public interface MainService {
    
    boolean addCard(String cardNumber) throws DuplicateCardException, InvalidCardException;

    List<CardDetails> getAllUserCards();

    StatementDetails getLatestStatement(String cardNumber) throws NoCardFoundException, NoStatementFoundException;

    StatementDetails getStatementFor(String cardNumber, int month, int year) throws NoCardFoundException, NoStatementFoundException;

    List<TransactionDetails> getTransactionsAfterDate(Date date);

    void addStatement(StatementDetails statementDetails) throws NoCardFoundException, InvalidStatementException;

    List<TransactionDetails> getTransactions(String cardNumber, String startDate, String endDate) throws NoCardFoundException;

    double addTransaction(TransactionDetails transaction) throws NoSufficientBalanceInCardException, CardLimitExceededException, NoCardFoundException, NoStatementFoundException;

    boolean addPaymentRecord(PaymentRecord record);

    boolean payCardBill(TransactionDetails transactionDetails);

    PaymentRecordDetails getPaymentRecord(String statementId);
}
