package com.baeldung.ditypes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Registrar {
    private User user;

    @Autowired
    public Registrar(User user) {
        this.user = user;
    }

    public String register() {
        return user.register();
    }
}
