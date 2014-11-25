package org.baeldung.persistence.service;

import org.baeldung.persistence.model.User;
import org.baeldung.persistence.model.VerificationToken;
import org.baeldung.validation.service.EmailExistsException;

public interface IUserService {

    public User registerNewUserAccount(UserDto accountDto) throws EmailExistsException;

    /*  public User getRegisteredUser(String email);*/

    public User getUser(String verificationToken);

    public void saveRegisteredUser(User user);

    public void addVerificationToken(User user, String token);

    public VerificationToken getVerificationToken(String VerificationToken);

    public void verifyUser(VerificationToken token);

    public void deleteUser(User user);
}
