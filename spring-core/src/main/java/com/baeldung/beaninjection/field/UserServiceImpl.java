package com.baeldung.beaninjection.field;

import com.baeldung.beaninjection.UserRepository;
import com.baeldung.beaninjection.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository repository;

    @PostConstruct
    public void init() {

    }
}
