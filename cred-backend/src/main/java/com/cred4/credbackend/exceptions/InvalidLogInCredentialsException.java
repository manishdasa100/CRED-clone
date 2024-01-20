package com.cred4.credbackend.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidLogInCredentialsException extends ApplicationException {

    public InvalidLogInCredentialsException(String msg) {
        super(msg, HttpStatus.BAD_REQUEST);
    }

}
