package com.cred4.credbackend.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidCardException extends ApplicationException{

    public InvalidCardException(String msg) {
        super(msg, HttpStatus.BAD_REQUEST);
    }
}