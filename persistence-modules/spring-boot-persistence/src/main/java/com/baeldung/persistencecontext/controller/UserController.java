package com.baeldung.persistencecontext.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.persistencecontext.entity.User;
import com.baeldung.persistencecontext.service.ExtendedPersistenceContextUserService;
import com.baeldung.persistencecontext.service.TransctionPersistenceContextUserService;

@RestController
public class UserController{

    @Autowired
    private TransctionPersistenceContextUserService transctionPersistenceContextUserService;
    @Autowired
    private ExtendedPersistenceContextUserService extendedPersistenceContextUserService;

    @RequestMapping(value = "v1/user", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public User saveUserV1(@RequestBody User user) {
        return transctionPersistenceContextUserService.insert(user);
    }

    @RequestMapping(value = "v2/user", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public User saveUserV2(@RequestBody User user) {
        return extendedPersistenceContextUserService.insert(user);
    }
    
    @RequestMapping(value = "v3/user", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public User saveUserV3(@RequestBody User user) {
        return extendedPersistenceContextUserService.insertWithManagedTransaction(user);
    }
    
    @RequestMapping(value = "v4/user", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public User saveUserV4(@RequestBody User user) {
        return transctionPersistenceContextUserService.insertWithoutTransaction(user);
    }

    @RequestMapping(value = "v1/user/{id}", method = RequestMethod.GET, produces = "application/json")
    public User getUserV1(@PathVariable("id") Long id) {
        return transctionPersistenceContextUserService.find(id);
    }

    @RequestMapping(value = "v2/user/{id}", method = RequestMethod.GET, produces = "application/json")
    public User getUserV2(@PathVariable("id") Long id) {
        return extendedPersistenceContextUserService.find(id);
    }
}
