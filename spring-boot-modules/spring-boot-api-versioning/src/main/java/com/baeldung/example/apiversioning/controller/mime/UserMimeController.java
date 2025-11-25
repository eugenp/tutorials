// src/main/java/com/baeldung/example/apiversioning/controller/mime/UserMimeController.java
package com.baeldung.example.apiversioning.controller.mime;

import com.baeldung.example.apiversioning.model.UserV1;
import com.baeldung.example.apiversioning.model.UserV2;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserMimeController {

    public static final String V1_MEDIA = "application/vnd.example.users-v1+json";
    public static final String V2_MEDIA = "application/vnd.example.users-v2+json";

    @GetMapping(value = "/api/users", produces = V1_MEDIA)
    public List<UserV1> usersV1() {
        return List.of(new UserV1("Alice"), new UserV1("Bob"));
    }

    @GetMapping(value = "/api/users", produces = V2_MEDIA)
    public List<UserV2> usersV2() {
        return List.of(
            new UserV2("Alice", "alice@example.com", 30),
            new UserV2("Bob", "bob@example.com", 25)
        );
    }

    // Optional fallback
    @GetMapping(value = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserV1> defaultUsers() {
        return List.of(new UserV1("Alice"), new UserV1("Bob"));
    }
}