package com.baeldung.skipselectbeforeinsert.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.baeldung.skipselectbeforeinsert.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer>, TaskRepositoryExtension {

    @Modifying
    @Query(value = "insert into task(id, description) values(:#{#task.id}, :#{#task.description})", nativeQuery = true)
    void insert(@Param("task") Task task);
}
