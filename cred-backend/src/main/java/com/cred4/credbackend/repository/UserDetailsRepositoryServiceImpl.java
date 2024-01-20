package com.cred4.credbackend.repository;

import java.util.Optional;

import com.cred4.credbackend.exceptions.UserAlreadyExistException;
import com.cred4.credbackend.models.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsRepositoryServiceImpl implements UserDetailsRepositorySevice {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Optional<User> loadUser(String userName) {
        Query query = new Query(Criteria.where("userName").is(userName));
        User user = mongoTemplate.findOne(query, User.class, "Users");
        return Optional.ofNullable(user);
    }

    @Override
    public void saveUser(User user) throws UserAlreadyExistException {
        
        try{
            mongoTemplate.insert(user, "Users");
        } catch(DuplicateKeyException exception) {
            throw new UserAlreadyExistException("Username already exist");
        }
        
    }
    
}
