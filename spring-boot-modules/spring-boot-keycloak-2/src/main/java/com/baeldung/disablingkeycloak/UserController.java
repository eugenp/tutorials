package com.baeldung.disablingkeycloak;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping("/{userId}")
    public User getCustomer(@PathVariable(name = "userId") Long userId) {
        return new User(userId, "John", "Doe");
    }

}
