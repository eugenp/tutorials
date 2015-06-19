package org.baeldung.web.util;


import java.util.Date;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import org.baeldung.persistence.dao.ActiveUserRepository;
import org.baeldung.persistence.dao.UserRepository;
import org.baeldung.persistence.model.ActiveUser;
import org.baeldung.web.cache.ActiveUserCache;

public class ActiveUserSessionBindingListener implements HttpSessionBindingListener {
    public static final String SESSION_USER = "sessionUser";
    
    private ActiveUser activeUser;
    private UserRepository userRepository;
    private ActiveUserRepository activeUserRepository;
    
    public ActiveUserSessionBindingListener(String userEmail, String ipAddress, UserRepository userRepository) {
        setUserRepository(userRepository);
        createActiveUser(userEmail, ipAddress);
    }

    private void createActiveUser(String userEmail, String ipAddress) {
        activeUser = new ActiveUser();
        activeUser.setActiveTime(new Date(System.currentTimeMillis()));
        activeUser.setIpAddress(ipAddress);
        activeUser.setUser(userRepository.findByEmail(userEmail));
    }
   
    public void valueBound(HttpSessionBindingEvent event) {
        activeUserRepository.save(activeUser);
        ActiveUserCache.getInstance().save(activeUser);
    }

    public void valueUnbound(HttpSessionBindingEvent event) {
        activeUserRepository.delete(activeUser);
        ActiveUserCache.getInstance().delete(activeUser);
    }
    
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    public void setActiveUserRepository(ActiveUserRepository activeUserRepository) {
        this.activeUserRepository = activeUserRepository;
    }

}
