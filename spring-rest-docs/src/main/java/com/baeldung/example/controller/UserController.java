package com.baeldung.example.controller;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.example.exception.ValidationException;
import com.baeldung.example.model.User;
import com.baeldung.example.service.UserService;

@RestController
@RequestMapping(value = "users")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping
    public ResponseEntity<Set<User>> getUsers() {
        Set<User> users = userService.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @RequestMapping(value = "{userName}", produces = "application/json")
    public ResponseEntity<User> getUser(@PathVariable String userName) {
        Optional<User> userOptional = userService.findUserByName(userName);
        if (!userOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        User user = userOptional.get();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @RequestMapping(value = "{userName}", method = PUT, consumes = "application/json", produces = "application/json")
    public ResponseEntity<User> updateUser(@PathVariable String userName, @RequestBody User newUser) {
        if (!userService.findUserByName(userName).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        User updatedUser = userService.updateUser(userName, newUser);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @RequestMapping(method = POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<User> createUser(@RequestBody User newUser) {
        if (userService.findUserByName(newUser.getName()).isPresent()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        boolean isUserCreated = userService.createUser(newUser);
        if (isUserCreated) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "{userName}", method = DELETE)
    public ResponseEntity<User> deleteUser(@PathVariable String userName) {
        boolean isUserDeleted = userService.deleteUser(userName);
        if (isUserDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @ExceptionHandler(value = ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void validationExceptionHandler(ValidationException ve) {
        LOGGER.error("Got exception", ve);
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void exceptionHandler(Exception e) {
        LOGGER.error("Got exception", e);
    }
}
