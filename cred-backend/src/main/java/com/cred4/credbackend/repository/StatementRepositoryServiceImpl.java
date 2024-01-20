package com.cred4.credbackend.repository;

import java.util.Optional;

import com.cred4.credbackend.dto.StatementDetails;
import com.cred4.credbackend.exceptions.InvalidStatementException;
import com.cred4.credbackend.models.Statement;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class StatementRepositoryServiceImpl implements StatementRepositoryService{

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private ModelMapper mapper;

    private static final String COLLECTION_NAME = "Statement";

    @Override
    public Optional<StatementDetails> getLatestStatementForCard(String cardNumber) {

        Query query = new Query(Criteria.where("cardNumber").is(cardNumber)).with(Sort.by(Direction.DESC, "_id"));

        Statement lastestStatement = mongoTemplate.findOne(query, Statement.class, COLLECTION_NAME);

        StatementDetails details = null;

        if (lastestStatement != null) {
            details = mapper.map(lastestStatement, StatementDetails.class);
        }
        
        return Optional.ofNullable(details);
    }

    @Override
    public Optional<StatementDetails> getStatementFor(String cardNumber, int month, int year) {
        
        Query query = new Query(
                        Criteria.where("cardNumber").is(cardNumber)
                        .andOperator(
                            Criteria.where("month(billStartDate)").is(month)
                            // Criteria.where("year(billStartDate)").is(year)
                        ));

        Statement statement = mongoTemplate.findOne(query, Statement.class, COLLECTION_NAME);

        StatementDetails statementDetails = null;

        if (statement != null) {
            statementDetails = mapper.map(statement, StatementDetails.class);
        }

        return Optional.ofNullable(statementDetails);
    }

    
    @Override
    public Optional<StatementDetails> getStatement(String statementId) {
        
        Statement statement = mongoTemplate.findById(statementId, Statement.class, COLLECTION_NAME);

        StatementDetails statementDetails = null;

        if (statement != null){
            statementDetails = mapper.map(statement, StatementDetails.class);
        }

        return Optional.ofNullable(statementDetails);
    }

    @Override
    public void addStatement(StatementDetails statementDetails) throws InvalidStatementException{

        Query query = new Query(
                            Criteria.where("cardNumber").is(statementDetails.getCardNumber())
                            .andOperator(Criteria.where("billEndDate").gte(statementDetails.getBillStartDate()))
                        );

        Statement prevStatement = mongoTemplate.findOne(query, Statement.class, COLLECTION_NAME);

        if (prevStatement != null) {
            throw new InvalidStatementException("Billing dates clasing with previous statement. Please check the dates");
        }

        Statement statement = mapper.map(statementDetails, Statement.class);
        
        mongoTemplate.insert(statement, COLLECTION_NAME);
    }

    @Override
    public void removeStatement(String statementId) {
        
        
    }

    @Override
    public void setStatementPaymentStatus(String statementId, boolean paymentStatus) {
        
        Query query = new Query(Criteria.where("statementId").is(statementId));

        Update update = new Update();
        update.set("paymentStatus", paymentStatus);

        mongoTemplate.updateFirst(query, update, Statement.class, COLLECTION_NAME);
    }
    
}
