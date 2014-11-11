package org.baeldung.persistence.service;

import org.baeldung.persistence.model.User;
import org.baeldung.validation.service.EmailExistsException;

public interface IUserService {

    public User registerNewUserAccount(UserDto accountDto) throws EmailExistsException;

    public User getRegisteredUser(String email);
    
    public User getUser(String verificationToken);
    
    public void verifyRegisteredUser(User user);
    
    public void addVerificationToken(User user, String token); 
}
