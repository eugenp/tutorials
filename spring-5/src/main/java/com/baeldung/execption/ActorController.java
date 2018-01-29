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

    // Response based on @ResponseStatus
    @GetMapping("/leadActor/{id}")
    public String getLeadActorName(@PathVariable("id") int id) throws LeadActorNotFoundException {
        return actorService.getLeadActor(id);
    }

    // Response based on Global Exception Handler, overriding @ResponseStatus
    @GetMapping("/leadActress/{id}")
    public String getLeadActressName(@PathVariable("id") int id) throws LeadActressNotFoundException {
        return actorService.getLeadActress(id);
    }

    // Response based on ResponseStatusException
    @GetMapping("/actor/{id}")
    public String getActorName(@PathVariable("id") int id) {
        try {
            return actorService.getActor(id);
        } catch (ActorNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Actor Not Found", ex);
        }
    }

    // Response based on Global Exception Handler, overriding ResponseStatusException
    @GetMapping("/actress/{id}")
    public String getActressName(@PathVariable("id") int id) {
        try {
            return actorService.getActress(id);
        } catch (ActressNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Actress Not Found", ex);
        }
    }

}
