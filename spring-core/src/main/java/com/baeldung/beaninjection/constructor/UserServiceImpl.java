package com.baeldung.beaninjection.constructor;

import com.baeldung.beaninjection.UserRepository;
import com.baeldung.beaninjection.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }
}
