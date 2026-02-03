package com.baeldung.hateoasvsswagger;

import com.baeldung.hateoasvsswagger.model.NewUser;
import com.baeldung.hateoasvsswagger.model.User;
import com.baeldung.hateoasvsswagger.repository.UserRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository = new UserRepository();

    @Operation(summary = "Get all users", description = "Retrieve a list of all users")
    @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "List of users", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
      @ApiResponse(responseCode = "500", description = "Internal server error") })
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok()
          .body(userRepository.getAllUsers());
    }

    @Operation(summary = "Create a new user", description = "Add a new user to the system")
    @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "User created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
      @ApiResponse(responseCode = "400", description = "Invalid input") })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> createUser(
      @RequestBody(description = "User data", required = true, content = @Content(schema = @Schema(implementation = NewUser.class))) NewUser user) {
        return new ResponseEntity<>(userRepository.createUser(user), HttpStatus.CREATED);
    }

    @Operation(summary = "Get user by ID", description = "Retrieve a user by their unique ID")
    @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "User found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
      @ApiResponse(responseCode = "404", description = "User not found") })
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id) {
        return ResponseEntity.ok()
          .body(userRepository.getUserById(id));
    }
}

