package com.cred4.credbackend.exchanges;

import lombok.NoArgsConstructor;

import lombok.AllArgsConstructor;

import lombok.Data;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {
    
    private String userName;

    private String firstName;

    private String lastName;

    private String password;

}
