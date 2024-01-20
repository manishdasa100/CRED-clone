package com.cred4.credbackend.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.cred4.credbackend.dto.CardDetails;
import com.cred4.credbackend.dto.SmartStatementDetails;
import com.cred4.credbackend.dto.StatementDetails;
import com.cred4.credbackend.dto.TransactionDetails;
import com.cred4.credbackend.exceptions.InvalidStatementException;
import com.cred4.credbackend.exceptions.NoStatementFoundException;
import com.cred4.credbackend.models.TransactionByCategory;
import com.cred4.credbackend.models.TransactionByVendor;
import com.cred4.credbackend.models.TransactionType;
import com.cred4.credbackend.repository.StatementRepositoryService;
import com.cred4.credbackend.repository.TransactionRepositoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.stereotype.Service;

@Service
public class StatementServiceImpl implements StatementService {

    @Autowired
    private StatementRepositoryService statementRepo;

    @Autowired
    private TransactionRepositoryService transactionRepo;

    @Override
    public StatementDetails getLatestStatementDetails(String cardNumber) throws NoStatementFoundException {
        
        Optional<StatementDetails> optional = statementRepo.getLatestStatementForCard(cardNumber);

        optional.orElseThrow(() -> new NoStatementFoundException("No statements found for this card yet."));

        StatementDetails statementDetails = optional.get();

        statementDetails.setSmartStatementDetails(this.getSmartStatement(statementDetails));

        return statementDetails;
    }

    @Override
    public StatementDetails getStatementFor(String cardNumber, int month, int year) throws NoStatementFoundException {
        
        Optional<StatementDetails> optional = statementRepo.getStatementFor(cardNumber, month, year);

        optional.orElseThrow(() -> new NoStatementFoundException("No statement found for month "+month+" and year "+year));

        return optional.get();
    }


    @Override
    public StatementDetails getStatement(String statementId) throws NoStatementFoundException {
        
        Optional<StatementDetails> optional = statementRepo.getStatement(statementId);

        optional.orElseThrow(() -> new NoStatementFoundException("No statement found with id "+statementId));

        return optional.get();
    }


    @Override
    public double generateFullStatement(CardDetails cardDetails, StatementDetails statementDetails) throws InvalidStatementException {

        LocalDate currentStatementBillStartDate = LocalDate.parse(statementDetails.getBillStartDate());

        LocalDate currentStatementBillEndDate = LocalDate.parse(statementDetails.getBillEndDate());

        LocalDate dueDate = LocalDate.parse(statementDetails.getDueDate());

        if (currentStatementBillStartDate.isAfter(currentStatementBillEndDate) || currentStatementBillStartDate.isEqual(currentStatementBillEndDate) || currentStatementBillEndDate.isAfter(dueDate)) {
            throw new InvalidStatementException("Invalid Dates. Please check the statement dates");
        }

        double outstandingAmount = calculateOutStandingAmount(cardDetails, statementDetails);

        statementDetails.setCardLimit(cardDetails.getCardLimit());
        
        statementDetails.setOutstandingAmount(outstandingAmount);

        statementDetails.setPaymentStatus(false);

        statementRepo.addStatement(statementDetails);

        return outstandingAmount;
    }

    private double calculateOutStandingAmount(CardDetails cardDetails, StatementDetails statementDetails) {

        String billingPeriodStartDate = statementDetails.getBillStartDate();

        String billingPeriodEndDate = statementDetails.getBillEndDate();
        
        List<TransactionDetails> transactionDetails = transactionRepo.getAllTransactionsBetween(billingPeriodStartDate, billingPeriodEndDate, cardDetails.getCardNumber());

        double totalTransactionAmount = transactionDetails.stream()
                                            .mapToDouble(x -> {
                                                if (x.getType() == TransactionType.CREDIT) return -(x.getAmount());
                                                return x.getAmount(); 
                                            })
                                            .sum();

        double unpaidOutstanding = cardDetails.getOutstandingAmt();

        return unpaidOutstanding + totalTransactionAmount;
    }

    @Override
    public SmartStatementDetails getSmartStatement(StatementDetails statementDetails) {
        
        AggregationResults<TransactionByVendor> result1 = transactionRepo
                                                        .getAggregateTrasactionsByVendor(
                                                            statementDetails.getCardNumber(),
                                                            statementDetails.getBillStartDate(),
                                                            statementDetails.getBillEndDate()
                                                        );

        AggregationResults<TransactionByCategory> result2 = transactionRepo
                                                        .getAggregateTrasactionsByCategory(
                                                            statementDetails.getCardNumber(),
                                                            statementDetails.getBillStartDate(),
                                                            statementDetails.getBillEndDate()
                                                        );                                               

        SmartStatementDetails smartStatementDetails = new SmartStatementDetails(
            result1.getMappedResults(), result2.getMappedResults());

        return smartStatementDetails;
    }

    @Override
    public void setPaymentStatus(String statementId, boolean paymentStatus) {
        statementRepo.setStatementPaymentStatus(statementId, paymentStatus);
    }
    
}
