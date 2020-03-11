package com.baeldung.hexagonalarchitecture.core;

import com.baeldung.hexagonalarchitecture.port.INotificationManager;
import com.baeldung.hexagonalarchitecture.port.IUserRepository;

public class UserManagerApp {
    private INotificationManager logManager;
    private IUserRepository userRepository;

    public UserManagerApp(INotificationManager logManager, IUserRepository userRepository) {
        this.logManager = logManager;
        this.userRepository = userRepository;
    }

    public void login(String username, String password) {
        User user = userRepository.getUserByUsername(username);
        boolean successfulLogin
                = user != null && user.getPassword().equals(password);

        if (successfulLogin) {
            logManager.notify("User " + username + " has successfully logged in.");
        } else {
            logManager.notify("User " + username + " has failed to logged in.");
        }
    }

    public void register(String username, String password, String email) {
        boolean successfulRegistration = userRepository.addUser(new User(username, password, email));

        if (successfulRegistration) {
            logManager.notify("User " + username + " has successfully registered.");
        } else {
            logManager.notify("User " + username + " has failed to register.");
        }
    }
}
