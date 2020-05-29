package com.baeldung.jordaneval.hexagonalarchitecture.domain.ports;

import com.baeldung.jordaneval.hexagonalarchitecture.domain.ToDoList;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ToDoRepository {
    List<ToDoList> findAllLists();

    Optional<ToDoList> findListById( UUID id );

    ToDoList saveList( ToDoList list );
}
