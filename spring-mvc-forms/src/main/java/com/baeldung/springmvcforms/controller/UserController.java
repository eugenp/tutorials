package com.baeldung.springmvcforms.controller;

import com.baeldung.springmvcforms.domain.User;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class UserController {

    private List<User> users = Arrays.asList(
      new User("ana@yahoo.com", "pass", "Ana", 20),
      new User("bob@yahoo.com", "pass", "Bob", 30),
      new User("john@yahoo.com", "pass", "John", 40),
      new User("mary@yahoo.com", "pass", "Mary", 30));

    @GetMapping("/userPage")
    public String getUserProfilePage() {
        return "user";
    }

    @PostMapping("/user")
    @ResponseBody
    public ResponseEntity<Object> saveUser(@Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            final List<String> errors = result.getAllErrors().stream()
              .map(DefaultMessageSourceResolvable::getDefaultMessage)
              .collect(Collectors.toList());

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

    @GetMapping("/users")
    @ResponseBody
    public List<User> getUsers() {
        return users;
    }

}
