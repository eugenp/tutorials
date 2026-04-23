package com.baeldung.versioning.controller.v1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserV1Controller {
    @GetMapping
    public String getUsersV1() {
        return "User list from API v1";
    }
}