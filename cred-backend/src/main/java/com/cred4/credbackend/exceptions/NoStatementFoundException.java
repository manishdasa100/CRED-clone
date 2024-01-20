package com.cred4.credbackend.exceptions;

import org.springframework.http.HttpStatus;

public class NoStatementFoundException extends ApplicationException {
    
    public NoStatementFoundException(String msg) {
        super(msg, HttpStatus.NOT_FOUND);
    }

}
