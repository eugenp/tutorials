package com.baeldung.execption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class ActorController {

    @Autowired
    ActorService actorService;

    @GetMapping("/actor/{id}")
    public String getActorName(@PathVariable("id") int id) {
        try {
            return actorService.getActor(id);
        } catch (ActorNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Actor Not Found", ex);
        }
    }
}
