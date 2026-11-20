package com.baeldung.versioning.controller.v2;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v2/users")
public class UserV2Controller {
    @GetMapping
    public String getUsersV2() {
        return "User list from API v2 with extra fields";
    }
}