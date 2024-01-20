package com.cred4.credbackend.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Users")
public class User {
    
    @Id
    private String id;

    @Indexed(unique = true)
    private String userName;

    private String firstName;

    private String lastName;

    private String password;

    Roles role;

    public User(String userName, String firstName, String lastName, String password, Roles role) {
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }
}
