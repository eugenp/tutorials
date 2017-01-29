package org.baeldung.user.service;

import org.baeldung.user.dao.MyUserDAO;
import org.baeldung.user.model.MyUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MyUserService implements UserService {

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
