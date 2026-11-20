package com.baeldung.hateoasvsswagger;

import com.baeldung.hateoasvsswagger.model.NewUser;
import com.baeldung.hateoasvsswagger.model.User;
import com.baeldung.hateoasvsswagger.repository.UserRepository;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/hateoas/users")
public class UserHateoasController {

    private final UserRepository userService; // Assume a service layer handles business logic

    public UserHateoasController(UserRepository userService) {
        this.userService = userService;
    }

    @GetMapping
    public CollectionModel<User> getAllUsers() {
        List<User> users = userService.getAllUsers();

        users.forEach(user -> {
            user.add(linkTo(methodOn(UserController.class).getUserById(user.getId())).withSelfRel());
        });

        return CollectionModel.of(users, linkTo(methodOn(UserController.class).getAllUsers()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<User> getUserById(@PathVariable Integer id) {
        User user = userService.getUserById(id);
        user.add(linkTo(methodOn(UserController.class).getUserById(id)).withSelfRel());
        user.add(linkTo(methodOn(UserController.class).getAllUsers()).withRel("all-users"));
        return EntityModel.of(user);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<User>> createUser(@RequestBody NewUser user) {
        User createdUser = userService.createUser(user);
        createdUser.add(linkTo(methodOn(UserController.class).getUserById(createdUser.getId())).withSelfRel());
        return ResponseEntity.created(linkTo(methodOn(UserController.class).getUserById(createdUser.getId())).toUri())
          .body(EntityModel.of(createdUser));
    }
}

