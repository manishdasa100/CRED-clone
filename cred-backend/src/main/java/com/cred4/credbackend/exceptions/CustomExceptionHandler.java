package com.cred4.credbackend.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ExceptionInfo> handleException (ApplicationException error) {
        
        ExceptionInfo exceptionInfo = new ExceptionInfo(error.getMessage());
        
        return new ResponseEntity<ExceptionInfo>(exceptionInfo, error.getReponseStatus());
    }
    
}
