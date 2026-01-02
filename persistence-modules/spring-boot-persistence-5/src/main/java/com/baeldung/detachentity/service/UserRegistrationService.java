package com.baeldung.detachentity.service;

import org.springframework.stereotype.Service;

import com.baeldung.detachentity.client.UserApiClient;
import com.baeldung.detachentity.domain.User;

@Service
public class UserRegistrationService {
    private final UserDataService userDataService;
    private final UserApiClient userApiClient;

    public UserRegistrationService(UserDataService userDataService, UserApiClient userApiClient) {
        this.userDataService = userDataService;
        this.userApiClient = userApiClient;
    }

    public User handleRegistration(String name, String email) {
        User user = userDataService.createUser(name, email);

        if (userApiClient.verify(email)) {
            user = userDataService.activateUser(user.getId());
        }

        return user;
    }

}
