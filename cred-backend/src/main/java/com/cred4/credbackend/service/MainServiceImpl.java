package com.cred4.credbackend.service;

import java.sql.Statement;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.cred4.credbackend.dto.CardDetails;
import com.cred4.credbackend.dto.PaymentRecordDetails;
import com.cred4.credbackend.dto.StatementDetails;
import com.cred4.credbackend.dto.TransactionDetails;
import com.cred4.credbackend.exceptions.CardLimitExceededException;
import com.cred4.credbackend.exceptions.DuplicateCardException;
import com.cred4.credbackend.exceptions.InvalidCardException;
import com.cred4.credbackend.exceptions.InvalidStatementException;
import com.cred4.credbackend.exceptions.NoCardFoundException;
import com.cred4.credbackend.exceptions.NoStatementFoundException;
import com.cred4.credbackend.exceptions.NoSufficientBalanceInCardException;
import com.cred4.credbackend.models.PaymentRecord;
import com.cred4.credbackend.models.UserCard;
import com.cred4.credbackend.repository.UserCardRepositoryService;
import com.cred4.credbackend.utils.CardServiceFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class MainServiceImpl implements MainService {

    @Autowired
    private CardServiceFactory cardServiceFactory;

    @Autowired
    private UserCardRepositoryService userCardRepository;

    @Autowired
    private TransactionService transactionService;

    @Override
    public boolean addCard(String cardNumber) throws DuplicateCardException, InvalidCardException {
        
        // CardServiceAdapter adapter = cardServiceManager.getCardServiceAdapterInstance();

        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        
        CardService cardService = cardServiceFactory.verifyCardNumber(cardNumber);
        
        if (cardService != null) {
            System.out.println(cardService.getProvider());
            UserCard userCard = new UserCard(cardNumber, userName, cardService.getProvider());
            userCardRepository.addCard(userCard);
            cardService.addCard(cardNumber);
        } else {
            throw new InvalidCardException("Card number is not valid. Please provide a valid card number");
        }
        return false;
    }


    @Override
    public List<CardDetails> getAllUserCards() {

        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        
        List<UserCard> userCardnumbers = userCardRepository.getAllCardNumbersForUser(userName);

        System.out.println("The user card numbers are: ");

        List<CardDetails> allCardDetails = userCardnumbers.stream()
                        .map(x -> cardServiceFactory.findCardService(x.getCardProvider()).getCardDetails(x.getCardNumber()))
                        .collect(Collectors.toList());

        return allCardDetails;
    }

    @Override
    public StatementDetails getLatestStatement(String cardNumber) throws NoStatementFoundException, NoCardFoundException {
        
        Optional<UserCard> optional = userCardRepository.getCard(cardNumber);

        optional.orElseThrow(() -> new NoCardFoundException("Your card is not registered with us. Please register to proceed."));

        CardService cardService = cardServiceFactory.findCardService(optional.get().getCardProvider());

        StatementDetails details = cardService.getLatestStatementDetails(optional.get().getCardNumber());

        return details;
    }

    public void addStatement(StatementDetails statementDetails) throws NoCardFoundException, InvalidStatementException {

        Optional<UserCard> optional = userCardRepository.getCard(statementDetails.getCardNumber());

        optional.orElseThrow(() -> new NoCardFoundException("Your card is not registered with us. Please register to proceed."));
    
        CardService cardService = cardServiceFactory.findCardService(optional.get().getCardProvider());

        cardService.addStatement(statementDetails);
    }

    @Override
    public StatementDetails getStatementFor(String cardNumber, int month, int year) throws NoCardFoundException, NoStatementFoundException {
        
        Optional<UserCard> optional = userCardRepository.getCard(cardNumber);

        optional.orElseThrow(() -> new NoCardFoundException("Your card is not registered with us. Please register to proceed."));

        CardService cardService = cardServiceFactory.findCardService(optional.get().getCardProvider());

        StatementDetails details = cardService.getStatementFor(cardNumber, month, year);

        return details;
    }

    @Override
    public List<TransactionDetails> getTransactionsAfterDate(Date date) {
        
        return null;
    }

    // @Override
    // public boolean addStatement(StatementDetails statementDetails) {
    //     statementService.addStatement(statementDetails);
    //     return true;
    // }

    @Override
    public double addTransaction(TransactionDetails transactionDetails) throws NoSufficientBalanceInCardException, CardLimitExceededException, NoCardFoundException, NoStatementFoundException {
        
        Optional<UserCard> optional = userCardRepository.getCard(transactionDetails.getCardNumber());

        optional.orElseThrow(() -> new NoCardFoundException("Your card is not registered with us. Please register to proceed."));

        CardService cardService = cardServiceFactory.findCardService(optional.get().getCardProvider());

        // CardDetails cardDetails = cardService.getCardDetails(optional.get().getCardNumber());

        double currentCardBalance = transactionService.addTransaction(cardService, transactionDetails);

        // cardService.updateCurrentBalance(optional.get().getCardNumber(), currentCardBalance);

        return currentCardBalance;
    }

    @Override
    public List<TransactionDetails> getTransactions(String cardNumber, String startDate, String endDate) throws NoCardFoundException {

        Optional<UserCard> optional = userCardRepository.getCard(cardNumber);

        optional.orElseThrow(() -> new NoCardFoundException("Your card is not registered with us. Please register to proceed."));

        return transactionService.getTransactions(cardNumber, startDate, endDate);
    }

    @Override
    public boolean addPaymentRecord(PaymentRecord record) {
        
        return false;
    }

    @Override
    public boolean payCardBill(TransactionDetails transactionDetails) {
        
        return false;
    }

    @Override
    public PaymentRecordDetails getPaymentRecord(String statementId) {
        // TODO Auto-generated method stub
        return null;
    }
    
}
