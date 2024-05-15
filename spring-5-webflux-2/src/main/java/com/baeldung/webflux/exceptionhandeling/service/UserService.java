package com.baeldung.webflux.exceptionhandeling.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.baeldung.webflux.exceptionhandeling.ex.NotFoundException;
import com.baeldung.webflux.exceptionhandeling.model.User;
import com.baeldung.webflux.exceptionhandeling.repository.UserRepository;

import reactor.core.publisher.Mono;

public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Mono<User> getUserByIdThrowingException(String id) {
        User user = userRepository.findById(id);
        if (user == null)
          throw new NotFoundException("User Not Found");
        return Mono.justOrEmpty(user);
    }

    public Mono<User> getUserByIdUsingMonoError(String id) {
        User user = userRepository.findById(id);
        return (user != null) ? Mono.justOrEmpty(user) : Mono.error(new NotFoundException("User Not Found"));

    }
}
