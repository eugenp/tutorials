package com.baeldung.springmvcforms.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baeldung.springmvcforms.domain.User;

@Controller
public class UserController {

    List<User> users = new ArrayList<User>() {
        {
            add(new User("ana@yahoo.com", "pass", "Ana", 20));
            add(new User("bob@yahoo.com", "pass", "Bob", 30));
            add(new User("john@yahoo.com", "pass", "John", 40));
            add(new User("mary@yahoo.com", "pass", "Mary", 30));
        }
    };

    @GetMapping("/userPage")
    public String getUserProfilePage() {
        return "user";
    }

    @PostMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Object> saveUser(@Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            List<String> errors = new ArrayList<>();
            result.getAllErrors().forEach(item->{
                errors.add(item.getDefaultMessage());
            });
            return new ResponseEntity<>(errors, HttpStatus.OK);
        } else {
            if (users.stream().anyMatch(it -> user.getEmail().equals(it.getEmail()))) {
                return new ResponseEntity<>(Collections.singletonList("Email already exists!"), HttpStatus.CONFLICT);
            } else {
                users.add(user);
                return new ResponseEntity<>(HttpStatus.CREATED);
            }
        }
    }

    @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<User> getUsers() {
        return users;
    }

}
