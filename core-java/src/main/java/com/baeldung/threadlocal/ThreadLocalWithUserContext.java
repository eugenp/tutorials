package com.baeldung.threadlocal;


public class ThreadLocalWithUserContext implements Runnable {
    private static final ThreadLocal<Context> userContext = new ThreadLocal<>();
    private final Integer userId;
    private UserRepository userRepository = new UserRepository();

    public ThreadLocalWithUserContext(Integer userId) {
        this.userId = userId;
    }


    @Override
    public void run() {
        String userName = userRepository.getUserNameForUserId(userId);
        userContext.set(new Context(userName));
        System.out.println("thread context for given userId: " + userId + " is: " + userContext.get());
    }
}
