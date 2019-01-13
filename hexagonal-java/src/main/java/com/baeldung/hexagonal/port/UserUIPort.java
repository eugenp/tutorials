package com.baeldung.hexagonal.port;

import com.baeldung.domain.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface UserUIPort {

    @PostMapping("register")
    public void register(@RequestBody User request);

    @GetMapping("view/{id}")
    public User view(@PathVariable Integer id);

}
