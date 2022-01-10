package com.example.springboot.repository.postgres;

import com.example.springboot.domain.Task;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskPostgresDbRepository extends CrudRepository<Task, String> {
}
