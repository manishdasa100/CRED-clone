package com.cred4.credbackend.repository;

import java.time.LocalDate;
import java.util.List;

import com.cred4.credbackend.dto.TransactionDetails;
import com.cred4.credbackend.models.TransactionByCategory;
import com.cred4.credbackend.models.TransactionByVendor;

import org.springframework.data.mongodb.core.aggregation.AggregationResults;

public interface TransactionRepositoryService {
    
    List<TransactionDetails> getAllTransactionsBetween(String startDate, String endDate, String cardNumber);

    void addTransactionRecord(TransactionDetails transactionDetails);

    void deleteTransactionRecord(String transactionId);

    TransactionDetails getTransactionRecord(String transactionId);

    AggregationResults<TransactionByVendor> getAggregateTrasactionsByVendor(
        String cardNumber, String startDate, String endDate);

    AggregationResults<TransactionByCategory> getAggregateTrasactionsByCategory(
        String cardNumber, String startDate, String endDate);
}
