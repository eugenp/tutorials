package com.baeldung.hexagonalarchitecture.restapi;

import com.baeldung.hexagonalarchitecture.application.UserApplicationService;
import com.baeldung.hexagonalarchitecture.domain.user.User;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This class has function of "adapter" of request to "port" {@link UserApplicationService}
 *
 * @author José Carlos Mazella Junior
 * @version 1.0 10/12/2018
 */
@RestController
@AllArgsConstructor
public class UserController {

    private final UserApplicationService applicationService;

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return applicationService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable("id") final Long id) {
        return applicationService.getUserById(id);
    }

    @PostMapping("/users")
    public User createUser(@RequestBody final User user) {
        return applicationService.createUser(user);
    }

    @DeleteMapping("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable("id") final Long id) {
        applicationService.deleteUser(id);
    }

}
