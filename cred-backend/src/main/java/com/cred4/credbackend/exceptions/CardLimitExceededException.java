package com.cred4.credbackend.exceptions;

import org.springframework.http.HttpStatus;

public class CardLimitExceededException extends ApplicationException {
    
    public CardLimitExceededException(String msg) {
        super(msg, HttpStatus.BAD_REQUEST);
    }

}
