package com.baeldung.jordaneval.hexagonalarchitecture.adapters.persistence;

import com.baeldung.jordaneval.hexagonalarchitecture.domain.ToDoList;
import com.baeldung.jordaneval.hexagonalarchitecture.domain.ports.ToDoRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public interface H2Repository extends ToDoRepository, JpaRepository<ToDoListEntity, UUID> {
    @Override
    default List<ToDoList> findAllLists() {
        return findAll().stream()
                        .map( ToDoListEntity::getDomain )
                        .collect( Collectors.toList() );
    }

    @Override
    default Optional<ToDoList> findListById( UUID id ) {
        return findById( id ).map( ToDoListEntity::getDomain );
    }

    @Override
    default ToDoList saveList( ToDoList list ) {
        return save( ToDoListEntity.of( list ) ).getDomain();
    }
}
