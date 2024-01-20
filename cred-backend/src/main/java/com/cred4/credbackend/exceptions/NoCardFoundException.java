package com.cred4.credbackend.exceptions;

import org.springframework.http.HttpStatus;

public class NoCardFoundException extends ApplicationException {
    
    public NoCardFoundException(String msg) {
        super(msg, HttpStatus.NOT_FOUND);
    }

}
