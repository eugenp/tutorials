package com.midgetontoes.todolist.resource;

import com.midgetontoes.todolist.controller.ItemRestController;
import com.midgetontoes.todolist.model.List;
import org.springframework.hateoas.Resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class ListResource extends Resource {

    private final String name;

    public ListResource(List list) {
        super(list);
        this.name = list.getName();
        Long listId = list.getId();
        add(linkTo(methodOn(ItemRestController.class).readItems(listId)).withRel("items"));
    }

    public String getName() {
        return name;
    }
}
