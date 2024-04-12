package com.baeldung.skipselectbeforeinsert.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.baeldung.skipselectbeforeinsert.model.Task;
import com.baeldung.skipselectbeforeinsert.model.TaskWithGeneratedId;

@Repository
public interface TaskWithGeneratedIdRepository extends JpaRepository<TaskWithGeneratedId, Integer> {
}
