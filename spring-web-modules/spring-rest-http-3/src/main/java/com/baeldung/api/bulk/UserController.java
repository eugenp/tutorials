package com.baeldung.api.bulk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bulk")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping(path = "/users")
    public ResponseEntity<List<User>> createUsers(@RequestBody List<User> users) {
        List<User> newUsers = userRepository.createUsers(users);
        return ResponseEntity.ok(newUsers);
    }
}
