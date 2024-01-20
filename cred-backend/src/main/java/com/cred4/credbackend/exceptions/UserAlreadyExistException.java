package com.cred4.credbackend.exceptions;

import org.springframework.http.HttpStatus;

public class UserAlreadyExistException extends ApplicationException{
    
    public UserAlreadyExistException(String msg){
        super(msg, HttpStatus.BAD_REQUEST);
    }
}
