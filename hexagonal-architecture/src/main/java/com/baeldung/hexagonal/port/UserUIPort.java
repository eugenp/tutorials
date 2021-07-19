package com.baeldung.hexagonal.port;

import com.baeldung.hexagonal.domain.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface UserUIPort {

    @PostMapping("/users")
    String add(@RequestBody User user);

    @GetMapping("/users/{userId}")
    User getDetail(@PathVariable String userId);
}
