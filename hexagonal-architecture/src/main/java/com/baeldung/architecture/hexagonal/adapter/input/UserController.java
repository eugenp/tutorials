package com.baeldung.architecture.hexagonal.adapter.input;

import com.baeldung.architecture.hexagonal.domain.Post;
import com.baeldung.architecture.hexagonal.domain.PostOutDTO;
import com.baeldung.architecture.hexagonal.domain.User;
import com.baeldung.architecture.hexagonal.domain.UserOutDTO;
import com.baeldung.architecture.hexagonal.port.inbound.UserService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseBody
    public UserOutDTO createUser(@RequestBody @Valid User userDTO) {
        return userService.createUser(userDTO);
    }

    @GetMapping(value = "/{userId}")
    @ResponseBody
    public UserOutDTO getUser(@PathVariable @NotNull Integer userId) {
        return userService.getUser(userId);
    }

    @PostMapping(value = "/{userId}/post")
    @ResponseBody
    public PostOutDTO createPost(@PathVariable Integer userId, @RequestBody @Valid Post post) {
        return userService.createPost(userId, post);
    }

}
