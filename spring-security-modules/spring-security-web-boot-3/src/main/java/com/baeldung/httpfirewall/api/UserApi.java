package com.baeldung.httpfirewall.api;


import com.baeldung.httpfirewall.model.Response;
import com.baeldung.httpfirewall.model.User;
import com.baeldung.httpfirewall.service.UserService;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class UserApi {

    private final UserService userService;

    public UserApi(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Response> createUser(@RequestBody User user) {

        log.info("Create a user");
        if (StringUtils.isBlank(user.getId())) {
            log.info("No user id provided. So generating a random id");
            user.setId(UUID.randomUUID().toString());
        }

        userService.saveUser(user);
        log.info("User saved successfully with userId: {}", user.getId());
        Response response = Response
          .builder()
          .code(HttpStatus.CREATED.value())
          .message("User created successfully")
          .timestamp(System.currentTimeMillis())
          .build();

        URI location = URI.create("/users/" + user.getId());
        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("/{userId}")
    public User getUser(@PathVariable("userId") String userId) {
        log.info("Fetching the user with id {} ", userId);
        return userService.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No user exists with the given Id"));
    }

    @GetMapping()
    public List<User> getAllUsers() {
        log.info("Fetching all the users in the data store");
        return userService.findAll().orElse(new ArrayList<>());
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Response> deleteUser(@PathVariable("userId") String userId) {
        log.info("Deleting a user with id {}", userId);
        userService.deleteUser(userId);
        return ResponseEntity.ok(new Response(200, "The user has been deleted successfully", System.currentTimeMillis()));
    }
}
