package com.cred4.credbackend.service;

import java.util.List;

import com.cred4.credbackend.dto.CardDetails;
import com.cred4.credbackend.dto.TransactionDetails;
import com.cred4.credbackend.exceptions.CardLimitExceededException;
import com.cred4.credbackend.exceptions.NoCardFoundException;
import com.cred4.credbackend.exceptions.NoStatementFoundException;
import com.cred4.credbackend.exceptions.NoSufficientBalanceInCardException;
import com.cred4.credbackend.models.TransactionType;
import com.cred4.credbackend.repository.TransactionRepositoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService{

    @Autowired
    private TransactionRepositoryService transactionRepo;

    private class ProcessTransactionResult{

        double outstandingAmount;
        double currentBalance;

        ProcessTransactionResult(double outstandingAmount, double currentBalance) {
            this.outstandingAmount = outstandingAmount;
            this.currentBalance = currentBalance;
        }
    }

    @Override
    public double addTransaction(CardService cardService, TransactionDetails transactionDetails) throws NoSufficientBalanceInCardException, CardLimitExceededException, NoCardFoundException, NoStatementFoundException {
        
        String cardNumber = transactionDetails.getCardNumber();

        CardDetails cardDetails = cardService.getCardDetails(cardNumber);

        ProcessTransactionResult result = processTransaction(cardDetails, transactionDetails);
        
        // transactionDetails.setDateOfTransaction(LocalDate.now());

        transactionRepo.addTransactionRecord(transactionDetails);

        cardService.updateCurrentBalance(cardNumber, result.currentBalance);

        cardService.updateOutstandingAmt(cardNumber, result.outstandingAmount);

        if (transactionDetails.getType() == TransactionType.CREDIT){
            cardService.updateStatementPaymentStatus(cardNumber, result.outstandingAmount);
            cardService.setDueDate(cardNumber, result.outstandingAmount);
        }

        return result.currentBalance;
    }

    @Override
    public List<TransactionDetails> getTransactions(String cardNumber, String startDate, String endDate) {
        return transactionRepo.getAllTransactionsBetween(startDate, endDate, cardNumber);
    }

    private ProcessTransactionResult processTransaction(CardDetails cardDetails, TransactionDetails transactionDetails) throws NoSufficientBalanceInCardException, CardLimitExceededException {

        double currentBalance = cardDetails.getCurrentBalance();

        double outstandingAmount = cardDetails.getOutstandingAmt();

        if (transactionDetails.getType() == TransactionType.CREDIT) {
            
            // double creditAmount = Math.min(outstandingAmount, transactionDetails.getAmount());

            double amountAfterCredit = currentBalance +  transactionDetails.getAmount();

            if (amountAfterCredit > cardDetails.getCardLimit()) throw new CardLimitExceededException("You cannot exceed your maximum card limit"); 

            outstandingAmount = outstandingAmount - transactionDetails.getAmount(); 

            return new ProcessTransactionResult(outstandingAmount, amountAfterCredit);
        
        } 

        double debitAmount = transactionDetails.getAmount();

        if (currentBalance < debitAmount) throw new NoSufficientBalanceInCardException("You do not have sufficient balance for this transaction."); 

        double amountAfterDebit = currentBalance - debitAmount;

        return new ProcessTransactionResult(outstandingAmount, amountAfterDebit);
    }
    
}
