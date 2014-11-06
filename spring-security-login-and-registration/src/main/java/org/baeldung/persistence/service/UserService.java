package org.baeldung.persistence.service;

import javax.transaction.Transactional;

import org.baeldung.persistence.dao.UserRepository;
import org.baeldung.persistence.model.Role;
import org.baeldung.persistence.model.User;
import org.baeldung.persistence.model.VerificationToken;
import org.baeldung.validation.service.EmailExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository repository;

    @Transactional
    @Override
    public User registerNewUserAccount(UserDto accountDto) throws EmailExistsException {
        if (emailExist(accountDto.getEmail())) {
            throw new EmailExistsException("There is an account with that email adress: " + accountDto.getEmail());
        }
        User user = new User();
        user.setFirstName(accountDto.getFirstName());
        user.setLastName(accountDto.getLastName());
        user.setPassword(accountDto.getPassword());
        user.setEmail(accountDto.getEmail());
        // ROLE WILL ALWAYS BE USER. HARDCODING IT
        user.setRole(new Role(Integer.valueOf(1), user));
        //OCT 21 EMAIL VERIFICATION VERSION
        //MIGHT CHANGE HERE
        VerificationToken myToken = new VerificationToken(accountDto.getToken(),user);
        user.setVerificationToken(myToken);
        return repository.save(user);
    }

    private boolean emailExist(String email) {
        User user = repository.findByEmail(email);
        if (user != null) {
            return true;
        }
        return false;
    }
    
    //OCT 21 EMAIL VERIFICATION
    @Override
    public User getRegisteredUser(String email){
        
        User user =  repository.findByEmail(email);
        return user;
        
    }
   
    @Transactional
    @Override
    public void verifyRegisteredUser(User user){
        repository.save(user);
    }
}
