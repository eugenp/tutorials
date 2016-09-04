package org.baeldung.persistence.service;

import javax.transaction.Transactional;

import org.baeldung.persistence.model.MyUser;
import org.baeldung.persistence.multiple.dao.user.MyUserDAO;
import org.baeldung.web.MyUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class MyUserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    MyUserDAO myUserDAO;

    public MyUser registerNewUserAccount(final MyUserDto accountDto) throws Exception {
        if (usernameExists(accountDto.getUsername())) {
            throw new Exception("There is an account with that username: " + accountDto.getUsername());
        }
        final MyUser user = new MyUser();

        user.setUsername(accountDto.getUsername());
        user.setPassword(passwordEncoder.encode(accountDto.getPassword()));
        user.setName(accountDto.getName());
        user.setEnabled(true);
        return myUserDAO.save(user);
    }

    public MyUser getUserByUsername(final String username) {
        final MyUser user = myUserDAO.findByUsername(username);
        return user;
    }

    private boolean usernameExists(final String username) {
        final MyUser user = myUserDAO.findByUsername(username);
        if (user != null) {
            return true;
        }
        return false;
    }

}
