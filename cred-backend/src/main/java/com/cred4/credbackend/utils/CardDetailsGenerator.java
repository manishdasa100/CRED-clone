package com.cred4.credbackend.utils;

import java.time.LocalDate;

import com.cred4.credbackend.dto.CardDetails;
import com.cred4.credbackend.service.AppUserDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import io.netty.util.internal.ThreadLocalRandom;

@Component
public class CardDetailsGenerator {

    private static AppUserDetailsService userDetailsService;

    @Autowired
    public CardDetailsGenerator(AppUserDetailsService userDetailsService) {
        CardDetailsGenerator.userDetailsService = userDetailsService;
    }
    

    public static CardDetails generateRandomCardDetails(String cardNumber, String provider) {
        
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        
        String userFullName = userDetailsService.getUserFullName(userName);

        LocalDate expiryDate = getRandomExpiryDate();

        double currentBalance = getRandomBalance();

        CardDetails cardDetails = new CardDetails(cardNumber, expiryDate, userFullName, currentBalance, 90000, 0, "", provider);

        return cardDetails;
    }

    private static double getRandomBalance() {
        
        double minBalance = 20000;

        double maxBalance = 90000;

        // return ThreadLocalRandom.current().nextDouble(minBalance, maxBalance);

        return maxBalance;
    }

    private static LocalDate getRandomExpiryDate() {

        LocalDate maxExpiryDateLimit = LocalDate.now().plusDays(365*3);

        long startEpochDay = LocalDate.now().toEpochDay();

        long endEpochDay = maxExpiryDateLimit.toEpochDay();

        long randomDay = ThreadLocalRandom.current().nextLong(startEpochDay, endEpochDay);

        return LocalDate.ofEpochDay(randomDay);
    }

}
