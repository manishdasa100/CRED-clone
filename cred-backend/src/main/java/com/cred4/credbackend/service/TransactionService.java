package com.cred4.credbackend.service;

import java.util.List;

import com.cred4.credbackend.dto.TransactionDetails;
import com.cred4.credbackend.exceptions.CardLimitExceededException;
import com.cred4.credbackend.exceptions.NoCardFoundException;
import com.cred4.credbackend.exceptions.NoStatementFoundException;
import com.cred4.credbackend.exceptions.NoSufficientBalanceInCardException;

public interface TransactionService {
    
    double addTransaction(CardService cardService, TransactionDetails transactionDetails) throws NoSufficientBalanceInCardException, CardLimitExceededException, NoCardFoundException, NoStatementFoundException;

    List<TransactionDetails> getTransactions(String cardNumber, String startDate, String endDate);
}
