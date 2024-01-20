package com.cred4.credbackend.utils;

import java.util.List;

import com.cred4.credbackend.models.UserCard;
import com.cred4.credbackend.service.CardService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CardServiceFactory {

    private List<CardService> cardServices; 

    @Autowired 
    public CardServiceFactory(List<CardService> cardServices) {
        this.cardServices = cardServices;
    }

    public CardService findCardService(String providerName) {
        
        CardService cardService = null;

        for (CardService cs : cardServices) {
            if (cs.getProvider().equals(providerName)) {
                cardService = cs;
            }
        }

        return cardService;
    }

    public CardService verifyCardNumber(String cardNumber) {

        CardService cardService = null;

        for (CardService cs : cardServices) {
            if (cs.verifyCardNumber(cardNumber)) {
                cardService = cs;
            }
        }

        return cardService;
    }

}
