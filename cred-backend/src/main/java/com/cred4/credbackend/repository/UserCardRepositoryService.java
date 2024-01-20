package com.cred4.credbackend.repository;

import java.util.List;
import java.util.Optional;

import com.cred4.credbackend.exceptions.DuplicateCardException;
import com.cred4.credbackend.models.UserCard;

public interface UserCardRepositoryService {
    
    boolean addCard(UserCard userCard) throws DuplicateCardException;

    List<UserCard> getAllCardNumbersForUser(String userName);

    boolean removeCard(String cardNumber);

    Optional<UserCard> getCard(String cardNumber);
}
