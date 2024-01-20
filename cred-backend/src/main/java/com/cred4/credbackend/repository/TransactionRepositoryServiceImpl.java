package com.cred4.credbackend.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.cred4.credbackend.dto.TransactionDetails;
import com.cred4.credbackend.models.Transaction;
import com.cred4.credbackend.models.TransactionByCategory;
import com.cred4.credbackend.models.TransactionByVendor;
import com.cred4.credbackend.models.TransactionType;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.LimitOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class TransactionRepositoryServiceImpl implements TransactionRepositoryService {

    private static final String COLLECTION_NAME = "Transaction";

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private ModelMapper mapper;

    @Override
    public List<TransactionDetails> getAllTransactionsBetween(String startDate, String endDate,
            String cardNumber) {
        
        Query query = new Query(
                            Criteria.where("cardNumber").is(cardNumber)
                            .andOperator(
                                Criteria.where("dateOfTransaction").gte(startDate).lte(endDate)
                            )
                        );

        List<Transaction> transactions = mongoTemplate.find(query, Transaction.class, COLLECTION_NAME);

        List<TransactionDetails> transactionDetails = transactions.stream()
                                                            .map(x -> mapper.map(x, TransactionDetails.class))
                                                            .collect(Collectors.toList());

        return transactionDetails;
    }

    @Override
    public void addTransactionRecord(TransactionDetails transactionDetails) {
        Transaction transaction = mapper.map(transactionDetails, Transaction.class);
        mongoTemplate.insert(transaction, COLLECTION_NAME);
    }

    @Override
    public void deleteTransactionRecord(String transactionId) {
        
    }

    @Override
    public TransactionDetails getTransactionRecord(String transactionId) {
        
        return null;
    }

    @Override
    public AggregationResults<TransactionByVendor> getAggregateTrasactionsByVendor(
        String cardNumber, String startDate, String endDate) {

        MatchOperation matchOperation = Aggregation.match(
            Criteria.where("cardNumber").is(cardNumber)
            .andOperator(
                Criteria.where("dateOfTransaction").gte(startDate).lte(endDate)
                .andOperator(Criteria.where("type").is(TransactionType.DEBIT.name()))
            )
        );

        GroupOperation groupOperation = Aggregation.group("vendor").sum("amount").as("totalTransactionAmount");

        SortOperation sortByTotalTransactionAmt = Aggregation.sort(Direction.DESC, "totalTransactionAmount");
        
        LimitOperation limit = Aggregation.limit(4);

        Aggregation aggregation = Aggregation.newAggregation(
            matchOperation, 
            groupOperation, 
            sortByTotalTransactionAmt,
            limit
        );

        AggregationResults<TransactionByVendor> result = mongoTemplate.aggregate(
            aggregation, Transaction.class, TransactionByVendor.class);

        return result;
    }

    @Override
    public AggregationResults<TransactionByCategory> getAggregateTrasactionsByCategory(
        String cardNumber, String startDate, String endDate) {

        MatchOperation matchOperation = Aggregation.match(
            Criteria.where("cardNumber").is(cardNumber)
            .andOperator(
                Criteria.where("dateOfTransaction").gte(startDate).lte(endDate)
                .andOperator(Criteria.where("type").is(TransactionType.DEBIT.name()))
            )
        );

        GroupOperation groupOperation = Aggregation.group("category").sum("amount").as("totalTransactionAmount");

        SortOperation sortByTotalTransactionAmt = Aggregation.sort(Direction.DESC, "totalTransactionAmount");

        LimitOperation limit = Aggregation.limit(4);
        
        Aggregation aggregation = Aggregation.newAggregation(
            matchOperation, 
            groupOperation, 
            sortByTotalTransactionAmt,
            limit
        );

        AggregationResults<TransactionByCategory> result = mongoTemplate.aggregate(
            aggregation, Transaction.class, TransactionByCategory.class);

        return result;
    }
    
}
