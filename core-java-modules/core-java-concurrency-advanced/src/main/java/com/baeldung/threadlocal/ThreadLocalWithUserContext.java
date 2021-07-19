package com.baeldung.threadlocal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThreadLocalWithUserContext implements Runnable {
    private static final Logger LOG = LoggerFactory.getLogger(ThreadLocalWithUserContext.class);
    
    private static final ThreadLocal<Context> userContext = new ThreadLocal<>();
    private final Integer userId;
    private UserRepository userRepository = new UserRepository();

    ThreadLocalWithUserContext(Integer userId) {
        this.userId = userId;
    }


    @Override
    public void run() {
        String userName = userRepository.getUserNameForUserId(userId);
        userContext.set(new Context(userName));
        LOG.debug("thread context for given userId: " + userId + " is: " + userContext.get());
    }
}
