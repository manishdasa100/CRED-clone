package com.cred4.credbackend.service;

import java.util.Optional;

import com.cred4.credbackend.dto.AppUserDetails;
import com.cred4.credbackend.exceptions.UserAlreadyExistException;
import com.cred4.credbackend.models.Roles;
import com.cred4.credbackend.models.User;
import com.cred4.credbackend.repository.UserDetailsRepositorySevice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AppUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDetailsRepositorySevice userDetailsRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> user = userDetailsRepo.loadUser(username);

        user.orElseThrow(() -> new UsernameNotFoundException("User name " + username + " not found!"));

        return new AppUserDetails(user.get());
    }

    public void saveUser(String userName, String firstName, String lastName, String password, Roles role) throws UserAlreadyExistException {
        User user = new User(userName, firstName, lastName, password, role);
        userDetailsRepo.saveUser(user);
    }

    public String getUserFullName(String userName) {
        
        Optional<User> optional = userDetailsRepo.loadUser(userName);

        User user = optional.get();
        
        return user.getFirstName() + " " + user.getLastName();
    }
    
}
