package com.baeldung.hexagonal.springapp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.baeldung.hexagonal.core.TodoItem;
import com.baeldung.hexagonal.springapp.entity.TodoItemEntity;

@Mapper
public interface TodoItemMapper {

    TodoItemMapper INSTANCE = Mappers.getMapper(TodoItemMapper.class);

    TodoItemEntity toEntity(TodoItem model);

    default TodoItem toModel(TodoItemEntity entity) {
        return new TodoItem(entity.getDescription(), entity.getDueDate(), entity.getCreationDate(), entity.isActive());
    }

}
