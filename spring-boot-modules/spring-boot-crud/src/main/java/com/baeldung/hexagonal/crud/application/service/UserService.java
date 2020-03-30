package com.baeldung.hexagonal.crud.application.service;

import com.baeldung.hexagonal.crud.domain.entity.User;
import com.baeldung.hexagonal.crud.domain.ports.exception.UserNotFoundException;
import com.baeldung.hexagonal.crud.domain.ports.incoming.UserManagementPort;
import com.baeldung.hexagonal.crud.infrastructure.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserManagementPort {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findById(long id) {
        Optional<User> user = this.userRepository.findById(id);
        if(user.isPresent()){
            return user.get();
        }else{
            throw new UserNotFoundException("User with id ".concat(String.valueOf(id)).concat(" has not being found in database"));
        }
    }

    @Override
    public User addUser(String name, String email) {
        return this.userRepository.save(this.createUserEntity(name, email));
    }

    @Override
    public User updateUser(long id, String name, String email) {
        Optional<User> user = this.userRepository.findById(id);
        if(user.isPresent()){
            User userToUpdate = user.get();
            userToUpdate.setName(name);
            userToUpdate.setEmail(email);
            return this.userRepository.save(userToUpdate);
        }else{
            throw new UserNotFoundException("User with id ".concat(String.valueOf(id)).concat(" has not being found in database"));
        }
    }

    @Override
    public void deleteUser(long id) {
        Optional<User> user = this.userRepository.findById(id);
        if(user.isPresent()){
           this.userRepository.delete(user.get());
        }else{
            throw new UserNotFoundException("User with id ".concat(String.valueOf(id)).concat(" has not being found in database"));
        }
    }

    private User createUserEntity(String name, String email){
        return new User(name, email);
    }
}
