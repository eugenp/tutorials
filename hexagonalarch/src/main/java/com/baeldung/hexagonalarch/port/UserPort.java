package com.baeldung.hexagonalarch.port;

import com.baeldung.hexagonalarch.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface UserPort {

    @PostMapping("createUser")
    void create(@RequestBody User user);

    @GetMapping("getUser/{userid}")
    User getUser(@PathVariable Integer userid);
}
