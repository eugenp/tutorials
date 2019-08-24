package com.baeldung.hexagonal.springapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.baeldung.hexagonal.core.TodoItem;
import com.baeldung.hexagonal.core.TodoRepository;
import com.baeldung.hexagonal.springapp.entity.TodoItemEntity;
import com.baeldung.hexagonal.springapp.mapper.TodoItemMapper;

public interface JpaTodoRepository extends TodoRepository, CrudRepository<TodoItemEntity, Long> {

    @Override default Optional<TodoItem> getActiveTodoItemByDescription(String description) {
        return findActiveTodoItemByDescription(description).map(TodoItemMapper.INSTANCE::toModel);
    }

    @Query("SELECT item FROM TodoItemEntity item WHERE item.description = :description AND item.active = true")
    Optional<TodoItemEntity> findActiveTodoItemByDescription(@Param("description") String description);

    @Override default void save(TodoItem item) {
        save(TodoItemMapper.INSTANCE.toEntity(item));
    }
}
