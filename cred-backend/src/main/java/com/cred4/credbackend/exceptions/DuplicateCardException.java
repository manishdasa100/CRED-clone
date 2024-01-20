package com.cred4.credbackend.exceptions;

import org.springframework.http.HttpStatus;

public class DuplicateCardException extends ApplicationException {
    
    public DuplicateCardException(String msg) {
        super(msg, HttpStatus.BAD_REQUEST);
    }

}
