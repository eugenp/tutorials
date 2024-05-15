package com.baeldung.app.repository;

import org.springframework.data.repository.CrudRepository;

import com.baeldung.app.entity.Task;

public interface TaskRepository extends CrudRepository<Task, Long> {

}
