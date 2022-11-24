package com.baeldung.sample;

import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @QueryMapping("getUser")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUser() {
        return new User("John", "Doe");
    }

}
