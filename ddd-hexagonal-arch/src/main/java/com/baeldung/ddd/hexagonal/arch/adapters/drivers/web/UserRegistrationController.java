package com.baeldung.ddd.hexagonal.arch.adapters.drivers.web;

import com.baeldung.ddd.hexagonal.arch.core.domain.User;
import com.baeldung.ddd.hexagonal.arch.core.ports.drivers.UserActivation;
import com.baeldung.ddd.hexagonal.arch.core.ports.drivers.UserSignup;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserRegistrationController {
    private UserSignup userSignup;
    private UserActivation userActivation;

    public UserRegistrationController(UserSignup userSignup, UserActivation userActivation) {
        this.userSignup = userSignup;
        this.userActivation = userActivation;
    }

    @PostMapping("/signup")
    public void signup(@RequestBody User user) {
        this.userSignup.signup(user);
    }

    @PatchMapping("/{id}/activate")
    public void activate(@PathVariable("id") String userId) {
        this.userActivation.activate(userId);
    }
}
