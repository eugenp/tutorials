package com.baeldung.hexagonal.controllers;

import com.baeldung.hexagonal.adapters.UserUpdatePortAdapter;
import com.baeldung.hexagonal.entities.User;
import javax.validation.Valid;

import com.baeldung.hexagonal.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") long id, @Valid User user, BindingResult result, Model model) {
        UserUpdatePortAdapter userUpdatePortAdapter = new UserUpdatePortAdapter();
        userService.updateUser(id, user, result, userUpdatePortAdapter, model);
        return userUpdatePortAdapter.getPageName();
    }

}