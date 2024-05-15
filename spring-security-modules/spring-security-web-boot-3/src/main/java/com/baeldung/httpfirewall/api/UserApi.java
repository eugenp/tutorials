package com.baeldung.httpfirewall.api;

import com.baeldung.httpfirewall.model.Response;
import com.baeldung.httpfirewall.model.User;
import com.baeldung.httpfirewall.service.UserServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
public class UserApi {

    private final UserServiceImpl userServiceImpl;

    public UserApi(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @PostMapping
    public ResponseEntity<Response> createUser(@RequestBody User user) {
        if (StringUtils.isBlank(user.getId())) {
            user.setId(UUID.randomUUID().toString());
        }
        userServiceImpl.saveUser(user);
        Response response = new Response(HttpStatus.CREATED.value(), "User created successfully", System.currentTimeMillis());
        URI location = URI.create("/users/" + user.getId());
        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("/{userId}")
    public User getUser(@PathVariable("userId") String userId) {
        return userServiceImpl.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No user exists with the given Id"));
    }

    @GetMapping()
    public List<User> getAllUsers() {
        return userServiceImpl.findAll().orElse(new ArrayList<>());
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Response> deleteUser(@PathVariable("userId") String userId) {
        userServiceImpl.deleteUser(userId);
        return ResponseEntity.ok(new Response(200, "The user has been deleted successfully", System.currentTimeMillis()));
    }
}
