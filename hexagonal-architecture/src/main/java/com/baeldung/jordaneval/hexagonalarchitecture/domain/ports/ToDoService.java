package com.baeldung.jordaneval.hexagonalarchitecture.domain.ports;

import com.baeldung.jordaneval.hexagonalarchitecture.domain.ToDoList;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@RequiredArgsConstructor
public class ToDoService {
    private final ToDoRepository repository;

    public List<ToDoList> getAllLists() {
        return repository.findAllLists();
    }

    public ToDoList addNewList( ToDoList list ) {
        //validation logic here

        return repository.saveList( list );
    }

    public ToDoList updateList( UUID id, ToDoList list ) {
        //validation logic here

        list.setId( id );
        return repository.saveList( list );
    }

    public ToDoList getList( UUID id ) {
        return repository.findListById( id )
                         .orElseThrow( NoSuchElementException::new );
    }
}
