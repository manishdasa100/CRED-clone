package com.cred4.credbackend.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.cred4.credbackend.dto.CardDetails;
import com.cred4.credbackend.dto.StatementDetails;
import com.cred4.credbackend.dto.TransactionDetails;
import com.cred4.credbackend.exceptions.CardLimitExceededException;
import com.cred4.credbackend.exceptions.InvalidStatementException;
import com.cred4.credbackend.exceptions.NoCardFoundException;
import com.cred4.credbackend.exceptions.NoStatementFoundException;
import com.cred4.credbackend.exceptions.NoSufficientBalanceInCardException;
import com.cred4.credbackend.models.TransactionType;
import com.cred4.credbackend.models.UserCard;
import com.cred4.credbackend.repository.CreditCardRepositoryService;
import com.cred4.credbackend.utils.CardDetailsGenerator;
import com.cred4.credbackend.utils.LuhnValidation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MastercardServiceImpl implements CardService {

    private String PROVIDER = "MasterCard";

    private int cardNumberLength = 16;

    private String cardNumberStartingRange = "51-55";

    private String cardNumberPattern = "4-4-4-4";

    @Autowired
    private CreditCardRepositoryService creditCardRepo;

    @Autowired
    private StatementService statementService;

    @Override
    public boolean verifyCardNumber(String cardNumber) {
        
        String[] startingNumbers = cardNumberStartingRange.split("-");

        String cardStartingNumberStr = cardNumber.substring(0, startingNumbers[0].length());

        try{
            long cardStartingNumber = Integer.parseInt(cardStartingNumberStr);

            if ((startingNumbers.length == 1 && cardStartingNumber != Integer.parseInt(startingNumbers[0])) 
                || (startingNumbers.length == 2 && (cardStartingNumber < Integer.parseInt(startingNumbers[0]) 
                || cardStartingNumber > Integer.parseInt(startingNumbers[1])))
                || cardNumber.length() != cardNumberLength || !LuhnValidation.validate(cardNumber)) {
                return false;
            } 

            return true;

        } catch (Exception e) {
            return false;
        }
    }

    
    @Override
    public void addCard(String cardNumber) {

        CardDetails cardDetails = CardDetailsGenerator.generateRandomCardDetails(cardNumber, PROVIDER);

        creditCardRepo.addCard(cardDetails);
    }

    @Override
    public CardDetails getCardDetails(String cardNumber) {
        
        Optional<CardDetails> optional = creditCardRepo.getCardDetails(cardNumber);

        return optional.get();
    }

    
    @Override
    public void addStatement(StatementDetails statementDetails) throws InvalidStatementException {

        CardDetails cardDetails = this.getCardDetails(statementDetails.getCardNumber());

        double outstandingAmt = statementService.generateFullStatement(cardDetails, statementDetails);

        updateOutstandingAmt(cardDetails.getCardNumber(), outstandingAmt);

        updateDueDate(cardDetails.getCardNumber(), statementDetails.getDueDate());
    }

    @Override
    public StatementDetails getLatestStatementDetails(String cardNumber) throws NoStatementFoundException {
        
        StatementDetails statementDetails = statementService.getLatestStatementDetails(cardNumber);

        return statementDetails;
    }

    @Override
    public StatementDetails getStatementFor(String cardNumber, int month, int year) throws NoStatementFoundException {
        
        StatementDetails statementDetails = statementService.getStatementFor(cardNumber, month, year);

        return statementDetails;
    }

    @Override
    public List<TransactionDetails> getTransactionsFor(String statementId) {
        
        return null;
    }

    @Override
    public void updateCurrentBalance(String cardNumber, double updatedCurrentBalance) {
        
        Map<String, Object> update = new HashMap<>();
        update.put("currentBalance", updatedCurrentBalance);
        
        creditCardRepo.updateCardDetails(update, cardNumber);
    }

    @Override
    public void updateOutstandingAmt(String cardNumber, double currentOutstandingAmt) {
        
        Map<String, Object> update = new HashMap<>();
        update.put("outstandingAmt", currentOutstandingAmt);
        
        creditCardRepo.updateCardDetails(update, cardNumber);
    }

    @Override
    public void updateDueDate(String cardNumber, String dueDate){

        Map<String, Object> update = new HashMap<>();
        update.put("dueDate", dueDate);

        creditCardRepo.updateCardDetails(update, cardNumber);
    }

    @Override
    public void setDueDate(String cardNumber, double outstandingAmount){
        
        if (outstandingAmount == 0) {
            updateDueDate(cardNumber, "");
        }
    }

    public void updateStatementPaymentStatus(String cardNumber, double outstandingAmount) throws NoStatementFoundException{
        
        if (outstandingAmount == 0) {

            StatementDetails statementDetails = this.getLatestStatementDetails(cardNumber);

            String statementId = statementDetails.getStatementId();

            statementService.setPaymentStatus(statementId, true);
        }
    }

    
    @Override
    public String getProvider() {
        return PROVIDER;
    }
    
}
