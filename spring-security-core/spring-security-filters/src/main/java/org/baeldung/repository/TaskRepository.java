package org.baeldung.repository;

import java.util.List;

import org.baeldung.entity.Task;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreFilter;

/**
 * 
 * @author felipereis
 *
 */
public interface TaskRepository extends CrudRepository<Task, Long> {

    @Override
    @PostFilter("hasRole('MANAGER') or filterObject.assignee == authentication.name")
    List<Task> findAll();

    @Override
    @PreFilter("hasRole('MANAGER') or filterObject.assignee == authentication.name")
    <S extends Task> Iterable<S> save(Iterable<S> entities);
}
