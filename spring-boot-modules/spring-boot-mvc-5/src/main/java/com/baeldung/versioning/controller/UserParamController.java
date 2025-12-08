package com.baeldung.versioning.controller;

import com.baeldung.versioning.model.UserV2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserParamController {

@GetMapping("/api/users")
public Object getUsers(@RequestParam(name = "version", defaultValue = "1") String version) {
    if ("1".equals(version)) {
        return List.of("Alice", "Bob");
    } else if ("2".equals(version)) {
        return List.of(
            new UserV2("Alice", "alice@example.com", 30),
            new UserV2("Bob", "bob@example.com", 25)
        );
    } else {
        return "Unsupported API version";
    }
}

}