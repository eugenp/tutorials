package com.baeldung.springsessionmongodb.controller;

import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpringSessionMongoDBController {

    @GetMapping("/")
    public ResponseEntity<Integer> count(HttpSession session) {

        Integer counter = (Integer) session.getAttribute("count");

        if (counter == null) {
            counter = 1;
        } else {
            counter += 1;
        }

        session.setAttribute("count", counter);

        return ResponseEntity.ok(counter);
    }

}