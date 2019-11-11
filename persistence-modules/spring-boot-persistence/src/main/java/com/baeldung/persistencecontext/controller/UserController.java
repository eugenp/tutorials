package com.baeldung.persistencecontext.controller;

import com.baeldung.persistencecontext.entity.User;
import com.baeldung.persistencecontext.service.ExtendedPersistenceContextUserService;
import com.baeldung.persistencecontext.service.TransctionPersistenceContextUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private TransctionPersistenceContextUserService transctionPersistenceContext;
    @Autowired
    private ExtendedPersistenceContextUserService extendedPersistenceContext;

    @RequestMapping(value = "v1/user", method = RequestMethod.POST,
            consumes = "application/json",
            produces = "application/json")
    public User saveUserV1(@RequestBody User user) {
        return transctionPersistenceContext.insertWithTransaction(user);
    }

    @RequestMapping(value = "v2/user", method = RequestMethod.POST,
            consumes = "application/json",
            produces = "application/json")
    public User saveUserV2(@RequestBody User user) {
        return extendedPersistenceContext.insertWithoutTransaction(user);
    }

    @RequestMapping(value = "v3/user", method = RequestMethod.POST,
            consumes = "application/json",
            produces = "application/json")
    public User saveUserV3(@RequestBody User user) {
        return extendedPersistenceContext.insertWithTransaction(user);
    }

    @RequestMapping(value = "v4/user", method = RequestMethod.POST,
            consumes = "application/json",
            produces = "application/json")
    public User saveUserV4(@RequestBody User user) {
        return transctionPersistenceContext.insertWithoutTransaction(user);
    }

    @RequestMapping(value = "v1/user/{id}", method = RequestMethod.GET,
            produces = "application/json")
    public User getUserV1(@PathVariable("id") Long id) {
        return transctionPersistenceContext.find(id);
    }

    @RequestMapping(value = "v2/user/{id}", method = RequestMethod.GET,
            produces = "application/json")
    public User getUserV2(@PathVariable("id") Long id) {
        return extendedPersistenceContext.find(id);
    }
}
