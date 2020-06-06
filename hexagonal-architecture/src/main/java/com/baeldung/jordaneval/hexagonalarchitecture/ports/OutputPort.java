package com.baeldung.jordaneval.hexagonalarchitecture.ports;

import com.baeldung.jordaneval.hexagonalarchitecture.domain.ToDoList;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OutputPort {
    List<ToDoList> findAllLists();
    Optional<ToDoList> findListById( UUID id );
    ToDoList saveList( ToDoList list );
}
