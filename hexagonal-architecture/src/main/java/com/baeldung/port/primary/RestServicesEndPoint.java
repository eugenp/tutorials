package com.baeldung.port.primary;

import com.baeldung.client.request.CreateUserRequest;
import com.baeldung.client.response.CreateUserResponse;
import com.baeldung.client.response.GetUserResponse;
import com.baeldung.domain.User;
import com.baeldung.port.service.UserServicePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/services/rest/user/")
public class RestServicesEndPoint {

    @Autowired
    private UserServicePort userServicePort;

    @PostMapping("create")
    public CreateUserResponse createUser(@RequestBody CreateUserRequest request) {
        User newUser = userServicePort.createUser(request.getName(), request.getUsername(), request.getPassword(), request.getEmail(), request.getPhone());
        return new CreateUserResponse(newUser.getId(), newUser.getUsername());
    }

    @GetMapping("get")
    public GetUserResponse getUser(@RequestParam(name = "userId") Integer userId) {
        Optional<User> userOptional = userServicePort.getUser(userId);
        return userOptional.map(user -> new GetUserResponse(user.getId(), user.getName(), user.getUsername(), user.getEmail(), user.getPhone())).orElseGet(GetUserResponse::new);
    }
}
