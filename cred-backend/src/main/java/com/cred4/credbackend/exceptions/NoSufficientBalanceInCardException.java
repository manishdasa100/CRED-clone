package com.cred4.credbackend.exceptions;

import org.springframework.http.HttpStatus;

public class NoSufficientBalanceInCardException extends ApplicationException{
    
    public NoSufficientBalanceInCardException(String msg) {
        super(msg, HttpStatus.BAD_REQUEST);
    }

}
