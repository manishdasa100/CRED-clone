package com.cred4.credbackend.repository;

import java.util.Map;
import java.util.Optional;

import com.cred4.credbackend.dto.CardDetails;
import com.cred4.credbackend.exceptions.CardLimitExceededException;
import com.cred4.credbackend.models.CreditCard;
import com.cred4.credbackend.utils.CardDetailsGenerator;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class CreditCardRepositoryServiceImpl implements CreditCardRepositoryService {

    private static final String COLLECTION_NAME = "CreditCard";

    @Autowired
    private MongoTemplate mongoTemplate;

    private ModelMapper mapper = new ModelMapper();

    @Override
    public Optional<CardDetails> getCardDetails(String cardNumber) {
        
        Query query = new Query(Criteria.where("cardNumber").is(cardNumber));

        CreditCard card = mongoTemplate.findOne(query, CreditCard.class, COLLECTION_NAME);

        CardDetails details = null;

        if (card != null) {
            details = mapper.map(card, CardDetails.class);
        } 
        // else {
        //     details = CardDetailsGenerator.generateRandomCardDetails(cardNumber);
        //     addCard(details);
        // }

        return Optional.ofNullable(details);
    }

    @Override
    public void addCard(CardDetails cardDetails) {
        CreditCard card = mapper.map(cardDetails, CreditCard.class);
        mongoTemplate.insert(card, COLLECTION_NAME);
    }

    @Override
    public void deleteCard(String cardNumber) {
        Query query = new Query(Criteria.where("cardNumber").is(cardNumber));
        mongoTemplate.remove(query, CreditCard.class, COLLECTION_NAME);
    }

    // @Override
    // public double updateCurrentCardBalance(String cardNumber, double currentBalance) {
    //     Query query = new Query(Criteria.where("cardNumber").is(cardNumber));
    //     CreditCard card = mongoTemplate.findOne(query, CreditCard.class, COLLECTION_NAME);
        
    //     card.setCurrentBalance(currentBalance);

    //     mongoTemplate.save(card, COLLECTION_NAME);
        
    //     return card.getCurrentBalance();
    // }

    
    @Override
    public CreditCard updateCardDetails(Map<String, Object> updates, String cardNumber) {

        Query query = new Query(Criteria.where("cardNumber").is(cardNumber));

        Update update = new Update();

        for (String key : updates.keySet()) {
            update.set(key, updates.get(key));
        }

        CreditCard updatedCard = mongoTemplate.findAndModify(query, update, CreditCard.class, COLLECTION_NAME);

        return updatedCard;
    }
    
}
