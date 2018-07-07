package com.baeldung.execption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
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

    @DeleteMapping("/actor/{id}")
    public String getActor(@PathVariable("id") int id) {
        return actorService.removeActor(id);
    }

    @PutMapping("/actor/{id}/{name}")
    public String updateActorName(@PathVariable("id") int id, @PathVariable("name") String name) {
        try {
            return actorService.updateActor(id, name);
        } catch (ActorNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Provide correct Actor Id", ex);
        }
    }

}
