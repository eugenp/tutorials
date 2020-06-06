package com.baeldung.jordaneval.hexagonalarchitecture.ports;

import com.baeldung.jordaneval.hexagonalarchitecture.domain.ToDoList;

import java.util.List;
import java.util.UUID;

public interface InputPort {
    List<ToDoList> getAllLists();
    ToDoList addNewList( ToDoList list );
    ToDoList updateList( UUID id, ToDoList list );
    ToDoList getList( UUID id );
}
