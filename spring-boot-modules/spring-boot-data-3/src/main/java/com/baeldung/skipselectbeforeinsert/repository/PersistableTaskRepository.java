package com.baeldung.skipselectbeforeinsert.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baeldung.skipselectbeforeinsert.model.PersistableTask;
import com.baeldung.skipselectbeforeinsert.model.Task;

@Repository
public interface PersistableTaskRepository extends JpaRepository<PersistableTask, Integer> {

}
