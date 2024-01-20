package com.cred4.credbackend.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidStatementException extends ApplicationException{

    public InvalidStatementException(String msg) {
        super(msg, HttpStatus.BAD_REQUEST);
    }
}
