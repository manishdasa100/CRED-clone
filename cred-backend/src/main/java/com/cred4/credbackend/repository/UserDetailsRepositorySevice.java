package com.cred4.credbackend.repository;

import java.util.Optional;

import com.cred4.credbackend.exceptions.UserAlreadyExistException;
import com.cred4.credbackend.models.User;

public interface UserDetailsRepositorySevice {
    
    Optional<User> loadUser(String userName);

    void saveUser(User user) throws UserAlreadyExistException;

}
