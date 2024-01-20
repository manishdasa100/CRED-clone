package com.cred4.credbackend.controller;

import java.util.List;

import com.cred4.credbackend.dto.CardDetails;
import com.cred4.credbackend.dto.StatementDetails;
import com.cred4.credbackend.dto.TransactionDetails;
import com.cred4.credbackend.exceptions.CardLimitExceededException;
import com.cred4.credbackend.exceptions.DuplicateCardException;
import com.cred4.credbackend.exceptions.ExceptionInfo;
import com.cred4.credbackend.exceptions.InvalidCardException;
import com.cred4.credbackend.exceptions.InvalidLogInCredentialsException;
import com.cred4.credbackend.exceptions.InvalidStatementException;
import com.cred4.credbackend.exceptions.NoCardFoundException;
import com.cred4.credbackend.exceptions.NoStatementFoundException;
import com.cred4.credbackend.exceptions.NoSufficientBalanceInCardException;
import com.cred4.credbackend.exceptions.UserAlreadyExistException;
import com.cred4.credbackend.exchanges.AddCardRequest;
import com.cred4.credbackend.exchanges.AuthenticationRequest;
import com.cred4.credbackend.exchanges.AuthenticationResponse;
import com.cred4.credbackend.exchanges.CreateUserRequest;
import com.cred4.credbackend.models.Roles;
import com.cred4.credbackend.models.TransactionType;
import com.cred4.credbackend.service.AppUserDetailsService;
import com.cred4.credbackend.service.MainService;
import com.cred4.credbackend.utils.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class MainController {

    @Autowired
    private AppUserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MainService mainService;
    
    // public static final String BASE_URL = "/api";

    @Operation(summary = "For checking whether user is logged in")
    @GetMapping("/greet")
    public String greet() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return "Welcome " + name;
    }
    
    @Operation(summary = "Register a new user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User registered successfully", content = @Content),
        @ApiResponse(responseCode = "400", description = "Username already registered",
                        content = {
                            @Content(mediaType = "application/json",
                                schema = @Schema(implementation = ExceptionInfo.class)
                            )
                        }
        )
    })
    @PostMapping("/signup")
    public ResponseEntity<?> createUser(@RequestBody CreateUserRequest request) throws UserAlreadyExistException {
        String userName = request.getUserName();
        String firstName = request.getFirstName();
        String lastName = request.getLastName();
        String password = request.getPassword();
        userDetailsService.saveUser(userName, firstName, lastName, password, Roles.ROLE_USER);
        return ResponseEntity.ok("");
    }


    @Operation(summary = "Login to a registered account")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User logged in successfully",
                       content = {
                           @Content(mediaType = "application/json", 
                                    schema = @Schema(implementation = AuthenticationResponse.class)
                            )                    
                        } 
                    ),
        @ApiResponse(responseCode = "400", description = "Invalid username or password",
                        content = {
                            @Content(mediaType = "application/json",
                                schema = @Schema(implementation = ExceptionInfo.class)
                            )
                        }
        )
    })
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody AuthenticationRequest request) throws Exception{
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new InvalidLogInCredentialsException("Incorrect username or password");
        }  
            
        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUserName());

        final String jwt = JwtUtil.generateToken(userDetails);
        
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }


    @Operation(summary = "Add a new credit card")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Card added successfully", content = @Content),
        @ApiResponse(responseCode = "400", description = "Card number is not valid, Duplicate card",
                        content = {
                            @Content(mediaType = "application/json",
                                schema = @Schema(implementation = ExceptionInfo.class)
                            )
                        }
        )
    })
    @PostMapping("/addCard")
    public ResponseEntity<?> addUserCard(@RequestBody AddCardRequest request) throws DuplicateCardException, InvalidCardException {
        boolean stat = mainService.addCard(request.getCardNumber());
        return ResponseEntity.ok("Added card");
    }


    @Operation(summary = "Get all the cards added by a user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "List of cards added by the user", 
                        content = @Content(
                                    mediaType = "application/json", 
                                    array = @ArraySchema(schema = @Schema(implementation = CardDetails.class))
                                )
                    ),
        @ApiResponse(responseCode = "403", description = "If user is not authenticated")
    })
    @GetMapping("/userCards")
    public ResponseEntity<?> getAllUserCards() {
        List<CardDetails> userCardDetails = mainService.getAllUserCards();
        return ResponseEntity.ok(userCardDetails);
    }


    @Operation(summary = "Adds statement for a card")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Statement added", content = @Content),
        @ApiResponse(responseCode = "400", description = "If statement to be added is invalid",
                    content = {
                        @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionInfo.class)
                        )
                    }
        ),
        @ApiResponse(responseCode = "404", description = "If card is not already added",
                    content = {
                        @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionInfo.class)
                        )
                    }
        )
    })
    @PostMapping("/statement/add")
    public ResponseEntity<?> addStatement(@RequestBody StatementDetails statementDetails) 
            throws NoCardFoundException, InvalidStatementException {
        mainService.addStatement(statementDetails);
        return ResponseEntity.ok("Statement added");
    }

    
    @Operation(summary = "Gets statement for a card. If month and year are not provided then gets the latest statement for a card")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "The statement details",
                        content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = StatementDetails.class)
                            )
                        }
        ),
        @ApiResponse(responseCode = "403", description = "If user is not authenticated"),
        @ApiResponse(responseCode = "404", description = "No statement found for the card, Card is not already added",
                        content = {
                            @Content(mediaType = "application/json",
                                schema = @Schema(implementation = ExceptionInfo.class)
                            )
                        }
        )
    })
    @GetMapping("/statement")
    public ResponseEntity<?> getStatementFor(
            @RequestParam String cardNumber, 
            @RequestParam(required = false) Integer month, 
            @RequestParam(required = false) Integer year) 
            throws NoCardFoundException, NoStatementFoundException {

        if (month == null || year == null) {
            StatementDetails statementDetails = mainService.getLatestStatement(cardNumber);
            return ResponseEntity.ok(statementDetails);
        }
        
        StatementDetails statementDetails = mainService.getStatementFor(cardNumber, month, year);
        return ResponseEntity.ok(statementDetails);
    }


    @Operation(summary = "Adds a transaction data for a card")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Transaction added successfully", content = @Content),
        @ApiResponse(responseCode = "400", description = "No sufficient balance in card, Card limit exceeding",
                        content = {@Content(mediaType = "application/json", 
                                    schema = @Schema(implementation = ExceptionInfo.class))
                                }
        ),
        @ApiResponse(responseCode = "404", description = "The card is not already added",
                        content = {
                            @Content(mediaType = "application/json",
                                schema = @Schema(implementation = ExceptionInfo.class)
                            )
                        }
        )
    })
    @PostMapping("/transaction/add")
    public ResponseEntity<?> addTransaction(@RequestBody TransactionDetails transactionDetails) 
            throws NoSufficientBalanceInCardException, CardLimitExceededException, 
                    NoCardFoundException, NoStatementFoundException {
        double currentCardBalance = mainService.addTransaction(transactionDetails);
        return ResponseEntity.ok(currentCardBalance);
    } 


    @Operation(summary = "Gets the transations done by a credit card between two dates")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "List of transactions",
                        content = {
                            @Content(mediaType = "application/json",
                                array = @ArraySchema(schema = @Schema(implementation = TransactionDetails.class))
                            )
                        }
        ),
        @ApiResponse(responseCode = "403", description = "If user is not logged in"),
        @ApiResponse(responseCode = "404", description = "If card is not already added",
                    content = {
                        @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionInfo.class)
                        )
                    }
        )
    })
    @GetMapping("/transactions")  
    public ResponseEntity<?> getTransactions(String cardNumber, String startDate, String endDate) throws NoCardFoundException {
        List<TransactionDetails> transactions = mainService.getTransactions(cardNumber, startDate, endDate);
        return ResponseEntity.ok(transactions);
    }


    @Operation(summary = "Pays the bill for a credit card")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Payment successfull", content = @Content),
        @ApiResponse(responseCode = "400", description = "Card limit exceeding, If transaction category is not CREDIT",
                    content = {
                        @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionInfo.class)
                        )
                    }
        ),
        @ApiResponse(responseCode = "404", description = "Card not added, No statement found",
                    content = {
                        @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionInfo.class)
                        )
                    }
        )
    })
    @PostMapping("/payBill")
    public ResponseEntity<?> payBill(@RequestBody TransactionDetails transactionDetails) 
            throws NoSufficientBalanceInCardException, CardLimitExceededException, 
                    NoCardFoundException, NoStatementFoundException {
        if (transactionDetails.getType() == TransactionType.CREDIT){
            double currentCardBalance = mainService.addTransaction(transactionDetails);
            return ResponseEntity.ok(currentCardBalance);
        }
        return ResponseEntity.badRequest().body("You cannot perform debit operation here!!");
    }
}
