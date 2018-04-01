package com.midgetontoes.todolist.controller;

import com.midgetontoes.todolist.AccessDeniedException;
import com.midgetontoes.todolist.command.CreateListCommand;
import com.midgetontoes.todolist.command.UpdateListCommand;
import com.midgetontoes.todolist.model.List;
import com.midgetontoes.todolist.model.User;
import com.midgetontoes.todolist.resource.ListResource;
import com.midgetontoes.todolist.resource.ListResourceAssembler;
import com.midgetontoes.todolist.service.ListService;
import com.midgetontoes.todolist.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.security.Principal;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@ExposesResourceFor(List.class)
@RequestMapping("/lists")
public class ListRestController {

    @Autowired
    private ListService listService;

    @Autowired
    private UserService userService;

    @Autowired
    private EntityLinks entityLinks;

    @RequestMapping(method = RequestMethod.GET)
    public Resources<ListResource> readLists(Principal principal) {
        String username = principal.getName();
        Link link = linkTo(ListRestController.class).withSelfRel();
        return new Resources<ListResource>(new ListResourceAssembler().toResources(listService.findByUserUsername(username)), link);
    }

    @RequestMapping(value = "/{listId}", method = RequestMethod.GET)
    public ListResource readList(@PathVariable Long listId) {
        return new ListResourceAssembler().toResource(listService.findOne(listId));
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> createList(Principal principal, @RequestBody CreateListCommand createListCommand) {
        User user = userService.findByUsername(principal.getName()).orElseThrow(AccessDeniedException::new);
        List list = new List(createListCommand.getName(), user);
        list = listService.save(list);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(entityLinks.linkForSingleResource(List.class, list).toUri());
        return new ResponseEntity<Object>(responseHeaders, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{listId}", method = RequestMethod.PUT)
    public ListResource updateList(@PathVariable Long listId, @RequestBody UpdateListCommand updateListCommand) {
        List list = listService.findOne(listId);
        list.setName(updateListCommand.getName());
        list = listService.save(list);
        return new ListResourceAssembler().toResource(list);
    }

    @RequestMapping(value = "/{listId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteList(@PathVariable Long listId) {
        listService.delete(listId);
        return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
    }
}
