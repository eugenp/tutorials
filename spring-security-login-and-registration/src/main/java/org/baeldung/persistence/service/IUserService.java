package org.baeldung.persistence.service;

import org.baeldung.persistence.model.User;
import org.baeldung.persistence.model.VerificationToken;
import org.baeldung.validation.service.EmailExistsException;

public interface IUserService {

    public User registerNewUserAccount(UserDto accountDto) throws EmailExistsException;

    //OCT 21 EMAIL VERIFICATION
    public User getRegisteredUser(String email);
    
    public void verifyRegisteredUser(User user);
}
