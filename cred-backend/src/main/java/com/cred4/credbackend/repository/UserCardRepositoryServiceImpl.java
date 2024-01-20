package com.cred4.credbackend.repository;

import java.util.List;
import java.util.Optional;

import com.cred4.credbackend.exceptions.DuplicateCardException;
import com.cred4.credbackend.models.UserCard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class UserCardRepositoryServiceImpl implements UserCardRepositoryService{

    private static final String COLLECTION_NAME = "UserCard";

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public boolean addCard(UserCard userCard) throws DuplicateCardException {

        try {
            mongoTemplate.insert(userCard, COLLECTION_NAME);
        } catch(Exception exception){
            throw new DuplicateCardException("Card is already registered.");
        }
        
        return false;
    }

    @Override
    public List<UserCard> getAllCardNumbersForUser(String userName) {
        
        Query query = new Query(Criteria.where("userName").is(userName));

        List<UserCard> userCards = mongoTemplate.find(query, UserCard.class, COLLECTION_NAME);

        // List<String> userCardNumbers = userCards.stream()
        //                                     .map(x -> x.getCardNumber())
        //                                     .collect(Collectors.toList());

        return userCards;
    }

    @Override
    public boolean removeCard(String cardNumber) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Optional<UserCard> getCard(String cardNumber) {
        
        Query query = new Query(Criteria.where("cardNumber").is(cardNumber));

        UserCard userCard = mongoTemplate.findOne(query, UserCard.class, COLLECTION_NAME);

        return Optional.ofNullable(userCard);
    }
    
}
