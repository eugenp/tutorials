package com.baeldung.modifyrequest.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class UserController {
    Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping(value = "save")
    public ResponseEntity<String> saveUser(@RequestBody String user) {
        logger.info("save user info into database");
        ResponseEntity<String> responseEntity = new ResponseEntity<>(user, HttpStatus.CREATED);
        return responseEntity;
    }
}
