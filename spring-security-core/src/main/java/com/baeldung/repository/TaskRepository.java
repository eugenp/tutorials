package com.baeldung.repository;

import org.springframework.data.repository.CrudRepository;

import com.baeldung.entity.Task;

public interface TaskRepository extends CrudRepository<Task, Long> {

}
