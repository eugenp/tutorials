package com.baeldung.jordaneval.hexagonalarchitecture.adapters.persistence;

import com.baeldung.jordaneval.hexagonalarchitecture.domain.ToDoList;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Data
@Entity
public class ToDoListEntity {

    @Id
    @GeneratedValue
    private UUID id;

    private String contents;

    public static ToDoListEntity of( ToDoList toDoList ) {
        ToDoListEntity entity = new ToDoListEntity();
        entity.setId( toDoList.getId() );
        entity.setContents( toDoList.getContents() );

        return entity;
    }

    public ToDoList getDomain() {
        return ToDoList.builder()
                       .id( getId() )
                       .contents( getContents() )
                       .build();
    }
}
