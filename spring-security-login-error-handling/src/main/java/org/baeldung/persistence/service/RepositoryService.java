package org.baeldung.persistence.service;

import javax.transaction.Transactional;

import org.baeldung.persistence.dao.UserRepository;
import org.baeldung.persistence.model.Role;
import org.baeldung.persistence.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RepositoryService implements UserService {
   @Autowired
    private UserRepository repository;
    private PasswordEncoder passwordEncoder;
   
    @Autowired
    private Environment env;
    
    @Autowired
    public RepositoryService(PasswordEncoder passwordEncoder, UserRepository repository) {
        this.passwordEncoder = passwordEncoder;
        this.repository = repository;
    }  
    
    @Transactional
    @Override
    public User registerNewUserAccount(UserDto userAccountData) throws EmailExistsException {
        if (emailExist(userAccountData.getUsername())) {
         
            throw new EmailExistsException("There is an account with that email adress: " + userAccountData.getUsername());
        }
        
        User user = new User();    
        user.setFirstName(userAccountData.getFirstName());
        user.setLastName(userAccountData.getLastName());
        user.setPassword(userAccountData.getPassword());
        user.setUsername(userAccountData.getUsername());
        user.setRole(new Role(userAccountData.getRole(), user));
        return repository.save(user);       
    }
    
    private boolean emailExist(String email) {
        User user = repository.findByUsername(email);
        if (user != null) {
            return true;
        }
        return false;
    }
}
