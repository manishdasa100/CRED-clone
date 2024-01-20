package com.cred4.credbackend.exceptions;

import org.springframework.http.HttpStatus;

public class ApplicationException extends Exception {
    
    private HttpStatus reponseStatus = HttpStatus.INTERNAL_SERVER_ERROR;

    public ApplicationException(String msg) {
        super(msg);
    }

    public ApplicationException(String msg, HttpStatus status) {
        super(msg);
        this.reponseStatus = status;
    }

    public HttpStatus getReponseStatus() {
        return this.reponseStatus;
    }
}
