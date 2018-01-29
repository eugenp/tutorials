package com.baeldung.typesbeaninjection.constructorbased;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class UserService {

    // The UserRepository has a dependency on the UserService
    private UserRepository userRepository;

    // A constructor so that the Spring container can inject a UserRepository
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // below some business logic that can use the injected UserRepository
    @PostConstruct
    public void init() {
        System.out.println(userRepository.findOne());
    }
}
