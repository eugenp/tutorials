package com.baeldung.exception;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ActorService {
    List<String> actors = Arrays.asList("Jack Nicholson", "Marlon Brando", "Robert De Niro", "Al Pacino", "Tom Hanks");

    public String getActor(int index) throws ActorNotFoundException {
        if (index >= actors.size()) {
            throw new ActorNotFoundException("Actor Not Found in Repsoitory");
        }
        return actors.get(index);
    }

    public String updateActor(int index, String actorName) throws ActorNotFoundException {
        if (index >= actors.size()) {
            throw new ActorNotFoundException("Actor Not Found in Repsoitory");
        }
        actors.set(index, actorName);
        return actorName;
    }

    public String removeActor(int index) {
        if (index >= actors.size()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Actor Not Found in Repsoitory");
        }
        return actors.remove(index);
    }
}
