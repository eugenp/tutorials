
package com.baeldung.portsandadaptors.rest;

import com.baeldung.portsandadaptors.core.inports.ManageUser;
import com.baeldung.portsandadaptors.core.inports.QueryUser;
import com.baeldung.portsandadaptors.core.model.User;
import com.baeldung.portsandadaptors.core.outports.UserPersistence;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author DeependraTewari
 */
@Service
public class HttpFacade implements ManageUser, QueryUser {

    private final UserPersistence userDb;
    
    @Autowired
    public HttpFacade(UserPersistence userAdaptor) {
        this.userDb = userAdaptor;
    }
    
    @Override
    public User addNew(User u) {
        return userDb.save(u);
    }

    @Override
    public User update(User u) {
        return userDb.save(u);
    }

    @Override
    public User remove(User u) {
        return userDb.remove(u);
    }

    @Override
    public List<User> findByName(String name) {
        return userDb.query("firstname", name);
    }

    @Override
    public User findByID(Long id) {
        return userDb.get(id);
    }

    @Override
    public List<User> findByTwitterHandle(String handle) {
        return userDb.query("twitterHandle", handle);
    }
    
}
