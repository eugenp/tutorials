package org.baeldung.web.cache;


import java.util.LinkedList;
import java.util.List;

import org.baeldung.persistence.dao.ActiveUserRepository0;
import org.baeldung.persistence.model.ActiveUser;

public class ActiveUserCache implements ActiveUserRepository0 {
    List<ActiveUser> users = new LinkedList<ActiveUser>();
    
    private static final ActiveUserCache INSTANCE = new ActiveUserCache();
    
    public ActiveUserCache() {

    }

    @Override
    public void delete(ActiveUser user) {
        users.remove(user);
    }

    @Override
    public ActiveUser save(ActiveUser user) {
        users.add(user);
        return user;
    }
    
    public List<ActiveUser> getAll() {
        return new LinkedList<ActiveUser>(users);
    }
    
    public static ActiveUserCache getInstance() {
        return INSTANCE;
    }
}
