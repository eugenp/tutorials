package com.baeldung.jordaneval.hexagonalarchitecture.adapters.web;

import com.baeldung.jordaneval.hexagonalarchitecture.domain.ToDoList;
import com.baeldung.jordaneval.hexagonalarchitecture.domain.ports.ToDoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping( "/lists" )
@RequiredArgsConstructor
public class ToDoListRestController {
    private final ToDoService service;

    @GetMapping
    List<ToDoList> getAllLists() {
        return service.getAllLists();
    }

    @PostMapping
    ToDoList addNewList( @RequestBody ToDoList list ) {
        return service.addNewList( list );
    }

    @PutMapping( "/{id}" )
    ToDoList updateList( @PathVariable UUID id,
                         @RequestBody ToDoList list ) {
        return service.updateList( id, list );
    }

    @GetMapping( "/{id}" )
    ToDoList getList( @PathVariable UUID id ) {
        return service.getList( id );
    }
}
