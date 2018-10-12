package com.baeldung.hexagon.inbound;

import com.baeldung.hexagon.core.User;
import com.baeldung.hexagon.core.UserPort;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * @author Ali Dehghani
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserPort userPort;

    public UserController(UserPort userPort) {
        this.userPort = userPort;
    }

    @GetMapping("{id}")
    public Optional<User> get(@PathVariable Long id) {
        return userPort.find(id);
    }

    @PostMapping
    public void create(@RequestBody User user) {
        userPort.addUser(user);
    }
}
