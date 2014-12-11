package org.baeldung.jackson.jsonview;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

@RestController
public class UserController {

    @JsonView(Views.Public.class)
    @RequestMapping("/{id}")
    public Item getItemPublic(@PathVariable final int id) {
        return ItemManager.getById(id);
    }

    @JsonView(Views.Internal.class)
    @RequestMapping("/internal/{id}")
    public Item getItemInternal(@PathVariable final int id) {
        return ItemManager.getById(id);
    }
}