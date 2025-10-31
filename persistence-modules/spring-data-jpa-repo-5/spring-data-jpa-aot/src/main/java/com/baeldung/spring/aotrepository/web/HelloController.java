package com.baeldung.spring.aotrepository.web;

import static org.springframework.http.ResponseEntity.ok;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.baeldung.spring.aotrepository.repository.UserRepository;

@Controller
public class HelloController {

    private final UserRepository userRepository;

    public HelloController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("hello")
    public ResponseEntity<String> hello() {
        return ok("hello back");
    }

    @GetMapping("get-user")
    public ResponseEntity<String> getUser() {
        return ok(userRepository.findAll().toString());
    }
}
