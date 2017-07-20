package com.baeldung.egoebelbecker;

import org.springframework.stereotype.Component;

@Component(value = "tarantula")
public class Tarantula implements Pet {

    @Override
    public String speciesByName() {
        return "tarantula";
    }
}
