package com.baeldung.persistancecontext.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.persistancecontext.entity.User;
import com.baeldung.persistancecontext.service.ExtendedPersistenceContextUserService;
import com.baeldung.persistancecontext.service.TransctionPersistenceContextUserService;

@RestController
public class UserController{

    @Autowired
    private TransctionPersistenceContextUserService userServiceTransctionPersistenceContext1;
    @Autowired
    private ExtendedPersistenceContextUserService userServiceExtendedPersistenceContext1;

    @RequestMapping(value = "v1/user", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public User saveUserV1(@RequestBody User user) {
        return userServiceTransctionPersistenceContext1.insert(user);
    }

    @RequestMapping(value = "v2/user", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public User saveUserV2(@RequestBody User user) {
        return userServiceExtendedPersistenceContext1.insert(user);
    }
    
    @RequestMapping(value = "v3/user", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public User saveUserV3(@RequestBody User user) {
        return userServiceExtendedPersistenceContext1.insertWithManagedTransaction(user);
    }
    
    @RequestMapping(value = "v4/user", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public User saveUserV4(@RequestBody User user) {
        return userServiceTransctionPersistenceContext1.insertWithoutTransaction(user);
    }

    @RequestMapping(value = "v1/user/{id}", method = RequestMethod.GET, produces = "application/json")
    public User getUserV1(@PathVariable("id") Long id) {
        return userServiceTransctionPersistenceContext1.find(id);
    }

    @RequestMapping(value = "v2/user/{id}", method = RequestMethod.GET, produces = "application/json")
    public User getUserV2(@PathVariable("id") Long id) {
        return userServiceExtendedPersistenceContext1.find(id);
    }
}
