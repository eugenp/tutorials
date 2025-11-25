// src/main/java/.../controller/header/UserHeaderController.java
package com.baeldung.example.apiversioning.controller.header;

import com.baeldung.example.apiversioning.model.UserV1;
import com.baeldung.example.apiversioning.model.UserV2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserHeaderController {

    @GetMapping(value = "/api/users", headers = "X-API-VERSION=1")
public List<UserV1> getUsersV1() {
    return List.of(new UserV1("Alice"), new UserV1("Bob"));
}

@GetMapping(value = "/api/users", headers = "X-API-VERSION=2")
public List<UserV2> getUsersV2() {
    return List.of(
        new UserV2("Alice", "alice@example.com", 30),
        new UserV2("Bob", "bob@example.com", 25)
    );
}
}