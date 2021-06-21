package com.baeldung.ddd.hexagonal.arch.core.services;

import com.baeldung.ddd.hexagonal.arch.core.domain.User;
import com.baeldung.ddd.hexagonal.arch.core.ports.driven.NotificationService;
import com.baeldung.ddd.hexagonal.arch.core.ports.driven.UserRepository;
import com.baeldung.ddd.hexagonal.arch.core.ports.drivers.UserSignup;
import org.springframework.stereotype.Service;

@Service
class UserSignupService implements UserSignup {
    private UserRepository userRepository;
    private NotificationService notificationService;

    public UserSignupService(UserRepository userRepository, NotificationService notificationService) {
        this.userRepository = userRepository;
        this.notificationService = notificationService;
    }

    @Override
    public void signup(User user) {
        this.userRepository.save(user);
        //this.notificationService.sendMessage(user.getEmail(), "Activate your account. Link <a>activate</a>");
    }
}
