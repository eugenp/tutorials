package org.baeldung.user.service;

import org.baeldung.persistence.model.MyUser;
import org.baeldung.user.dao.MyUserDAO;
import org.baeldung.web.MyUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MyUserService implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    MyUserDAO myUserDAO;

    /*
     * (non-Javadoc)
     * @see org.baeldung.user.service.UserService#getUserByUsername(java.lang.String)
     */
    @Override
    public MyUser getUserByUsername(final String username) {
        return myUserDAO.findByUsername(username);
    }

    public MyUser registerNewUserAccount(final MyUserDto accountDto) throws Exception {
        if (usernameExists(accountDto.getUsername())) {
            throw new Exception("There is an account with that username: " + accountDto.getUsername());
        }
        final MyUser user = new MyUser();

        user.setUsername(accountDto.getUsername());
        user.setPassword(passwordEncoder.encode(accountDto.getPassword()));
        return myUserDAO.save(user);
    }

    public void removeUserByUsername(String username) {
        myUserDAO.removeUserByUsername(username);
    }

    private boolean usernameExists(final String username) {
        final MyUser user = myUserDAO.findByUsername(username);
        if (user != null) {
            return true;
        }
        return false;
    }

}
