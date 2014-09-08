package org.baeldung.persistence.service;

import javax.transaction.Transactional;
import org.baeldung.persistence.dao.UserRepository;
import org.baeldung.persistence.model.Role;
import org.baeldung.persistence.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {   
    @Autowired
    private UserRepository repository;
    
    @Transactional
    @Override
    public User registerNewUserAccount(UserDto userAccountData) throws EmailExistsException {
        if (emailExist(userAccountData.getEmail())) {

            throw new EmailExistsException("There is an account with that email adress: " + userAccountData.getEmail());
        }
        User user = new User();
        user.setFirstName(userAccountData.getFirstName());
        user.setLastName(userAccountData.getLastName());
        user.setPassword(userAccountData.getPassword());
        user.setEmail(userAccountData.getEmail());
        //ROLE WILL ALWAYS BE USER. HARDCODING IT
        user.setRole(new Role(Integer.valueOf(1),user));
        return repository.save(user);
    }

    private boolean emailExist(String email) {
        User user = repository.findByEmail(email);
        if (user != null) {
            return true;
        }
        return false;
    }
}
