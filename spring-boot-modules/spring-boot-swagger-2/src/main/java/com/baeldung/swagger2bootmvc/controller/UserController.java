package com.baeldung.swagger2bootmvc.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.baeldung.swagger2bootmvc.model.User;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@Controller
public class UserController {

    public UserController() {
        super();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/createUser", produces = "application/json; charset=UTF-8")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    @Operation(summary  = "Create user",
        description = "This method creates a new user")
    public User createUser(@Parameter(
                    name =  "firstName",
                    description  = "First Name of the user",
                    example = "Vatsal",
        required = true) @RequestParam String firstName) {
        User user = new User(firstName);
        return user;
    }
}
