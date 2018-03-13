package com.midgetontoes.todolist.controller;

import com.midgetontoes.todolist.command.CreateItemCommand;
import com.midgetontoes.todolist.model.Item;
import com.midgetontoes.todolist.model.List;
import com.midgetontoes.todolist.resource.ItemResource;
import com.midgetontoes.todolist.resource.ItemResourceAssembler;
import com.midgetontoes.todolist.service.ItemService;
import com.midgetontoes.todolist.service.ListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@ExposesResourceFor(Item.class)
@RequestMapping(value = "/lists/{listId}/items")
public class ItemRestController {

    @Autowired
    private ListService listService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private EntityLinks entityLinks;

    @RequestMapping(method = RequestMethod.GET)
    public Resources<ItemResource> readItems(@PathVariable Long listId) {
        Link link = linkTo(ItemRestController.class, listId).withSelfRel();
        return new Resources<ItemResource>(
                new ItemResourceAssembler().toResources(itemService.findByListId(listId)),
                link
        );
    }

    @RequestMapping(value = "/{itemId}", method = RequestMethod.GET)
    public ItemResource readItem(@PathVariable Long listId, @PathVariable Long itemId) {
        return new ItemResourceAssembler().toResource(itemService.findOne(itemId));
    }

    @RequestMapping(value = "/{itemId}/markAsCompleted", method = RequestMethod.PUT)
    public ItemResource markAsCompleted(@PathVariable Long listId, @PathVariable Long itemId) {
        return new ItemResourceAssembler().toResource(itemService.markAsCompleted(itemId));
    }

    @RequestMapping(value = "/{itemId}/markAsUncompleted", method = RequestMethod.PUT)
    public ItemResource markAsUncompleted(@PathVariable Long listId, @PathVariable Long itemId) {
        return new ItemResourceAssembler().toResource(itemService.markAsUncompleted(itemId));
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> createItem(@PathVariable Long listId, @RequestBody CreateItemCommand createItemCommand) {
        List list = listService.findOne(listId);
        Item item = new Item(createItemCommand.getDescription(), createItemCommand.getPriority(), list);
        item = itemService.save(item);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(linkTo(methodOn(ItemRestController.class).readItem(listId, item.getId())).toUri());
        return new ResponseEntity<Object>(responseHeaders, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{itemId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteItem(@PathVariable Long listId, @PathVariable Long itemId) {
        itemService.delete(itemId);
        return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
    }
}
