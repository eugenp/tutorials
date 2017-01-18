package org.baeldung.user.service;

import org.baeldung.user.dao.MyUserDAO;
import org.baeldung.user.model.MyUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MyDerbyUserService implements UserService {

    private final MyUserDAO myUserDAO;

    @Autowired
    public MyDerbyUserService(final MyUserDAO myUserDAO) {
        this.myUserDAO = myUserDAO;
    }

    /*
     * (non-Javadoc)
     * @see org.baeldung.user.service.UserService#getUserByUsername(java.lang.String)
     */
    @Override
    public MyUser getUserByUsername(final String username) {
        return myUserDAO.findByUsername(username);
    }
}
