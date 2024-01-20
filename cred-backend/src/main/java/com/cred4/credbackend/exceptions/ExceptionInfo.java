package com.cred4.credbackend.exceptions;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ExceptionInfo {
    
    private String messageString = "Something went wrong. Please try again";

    public ExceptionInfo(String messageString) {
        this.messageString = messageString;
    }

}
