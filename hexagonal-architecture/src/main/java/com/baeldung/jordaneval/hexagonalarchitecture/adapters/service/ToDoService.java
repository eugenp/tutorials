package com.baeldung.jordaneval.hexagonalarchitecture.adapters.service;

import com.baeldung.jordaneval.hexagonalarchitecture.adapters.persistence.H2Repository;
import com.baeldung.jordaneval.hexagonalarchitecture.domain.ToDoList;
import com.baeldung.jordaneval.hexagonalarchitecture.ports.InputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ToDoService implements InputPort
{
    private final H2Repository repository;

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
