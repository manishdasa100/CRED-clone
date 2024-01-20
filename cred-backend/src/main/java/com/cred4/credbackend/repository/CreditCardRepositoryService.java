package com.cred4.credbackend.repository;

import java.util.Map;
import java.util.Optional;

import com.cred4.credbackend.dto.CardDetails;
import com.cred4.credbackend.models.CreditCard;

public interface CreditCardRepositoryService {
    
    Optional<CardDetails> getCardDetails(String cardNumber);

    void addCard(CardDetails cardDetails);

    void deleteCard(String cardNumber);

    CreditCard updateCardDetails(Map<String, Object> updates, String cardNumber);
}
