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
@Document(collection = "UserCard")
public class UserCard {
    
    @Id
    private String id;

    @Indexed(unique = true)
    private String cardNumber;

    private String cardProvider;

    private String userName;

    public UserCard(String cardNumber, String userName, String cardProvider) {
        this.cardNumber = cardNumber;
        this.userName = userName;
        this.cardProvider = cardProvider;
    }

}
