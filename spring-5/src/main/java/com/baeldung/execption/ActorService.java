package com.baeldung.execption;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ActorService {
    List<String> actors = Arrays.asList("Jack Nicholson", "Marlon Brando", "Robert De Niro", "Al Pacino", "Tom Hanks");

    public String getActor(int index) throws ActorNotFoundException {
        if (index >= actors.size()) {
            throw new ActorNotFoundException("Actor Not Found in Repsoitory");
        }
        return actors.get(index);
    }
}
