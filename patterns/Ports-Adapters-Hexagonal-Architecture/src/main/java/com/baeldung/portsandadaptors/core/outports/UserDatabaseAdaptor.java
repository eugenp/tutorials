
package com.baeldung.portsandadaptors.core.outports;

import com.baeldung.portsandadaptors.core.model.User;
import com.baeldung.portsandadaptors.repository.UserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author DeependraTewari
 */
@Component
public class UserDatabaseAdaptor implements UserPersistence {
    
    private final UserRepository userRepo;

    @Autowired
    public UserDatabaseAdaptor(UserRepository repo) {
        this.userRepo = repo;
    }
    
    @Override
    public User save(User u) {
        return userRepo.save(u);
    }

    @Override
    public List<User> query(String field, String value) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public User get(Long id) {
        return userRepo.findById(id).get();
    }

    @Override
    public User remove(User u) {
        userRepo.delete(u);
        return u;
    }
}
