package com.midgetontoes.todolist.resource;


import com.midgetontoes.todolist.controller.ItemRestController;
import com.midgetontoes.todolist.controller.ListRestController;
import com.midgetontoes.todolist.model.Item;
import org.springframework.hateoas.Resource;

import java.time.LocalDateTime;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class ItemResource extends Resource {

    private final String description;

    private final Item.Priority priority;

    private final boolean completed;

    private final LocalDateTime completedAt;

    public ItemResource(Item item) {
        super(item);
        this.description = item.getDescription();
        this.priority = item.getPriority();
        this.completed = item.isCompleted();
        this.completedAt = item.getCompletedAt();

        Long itemId = item.getId();
        Long listId = item.getList().getId();
        if (item.isCompleted()) {
            add(linkTo(methodOn(ItemRestController.class).markAsUncompleted(listId, itemId)).withRel("mark-as-uncompleted"));
        }
        else {
            add(linkTo(methodOn(ItemRestController.class).markAsCompleted(listId, itemId)).withRel("mark-as-completed"));
        }
        add(linkTo(methodOn(ListRestController.class).readList(listId)).withRel("collection"));
    }

    public String getDescription() {
        return description;
    }

    public Item.Priority getPriority() {
        return priority;
    }

    public boolean isCompleted() {
        return completed;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }
}
