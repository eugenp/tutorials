package com.baeldung.swagger2pdf.controller;

import com.baeldung.swagger2pdf.objects.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@Api(value = "/api", description = "A controller for user management")
@RequestMapping("/user")
@ResponseStatus(HttpStatus.OK)
public class UserController {

    static List<User> users;

    static {
        users = new ArrayList<>();
        users.add(new User("Mark", "Thompson", 23));
        users.add(new User("Kate", "Jordan", 22));
    }

    @ApiOperation(value = "Retrieves all the users")
    @RequestMapping(value = "/users",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getUsers() {
        return users;
    }

    @ApiOperation(value = "Retrieves a user based on first name")
    @RequestMapping(value = "/user",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUser(@NonNull @RequestParam String firstName) {

        if (firstName.isEmpty()) {
            return null;
        }

        return users.stream()
                .filter(user -> user.getFirstName().equals(firstName))
                .findAny()
                .orElse(null);

    }

    @ApiOperation(value = "Adds a user.")
    @RequestMapping(value = "/user",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> addUser(@NonNull @RequestBody User user) {
        users.add(user);
        return users;
    }
}

