package com.baeldung.hexagonal.crud.application.controller;

import com.baeldung.hexagonal.crud.application.dto.UserDto;
import com.baeldung.hexagonal.crud.application.usecase.UserUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @Autowired
    private UserUseCase userUseCase;

    @GetMapping("/user/{id}")
    public ResponseEntity<UserDto> findUser(@PathVariable("id") long id) {
        return new ResponseEntity<>(this.userUseCase.findById(id), HttpStatus.OK);
    }

    @PostMapping("/adduser")
    public ResponseEntity<UserDto> addUser(String name, String email) {
        return new ResponseEntity<>(this.userUseCase.addUser(name, email), HttpStatus.OK);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable("id") long id, String name, String email) {
        return new ResponseEntity<>(this.userUseCase.updateUser(id, name, email), HttpStatus.OK);
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<UserDto> deleteUser(@PathVariable("id") long id) {
        this.userUseCase.deleteUser(id);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

}
