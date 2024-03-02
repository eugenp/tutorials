package com.baeldung.skipselectbeforeinsert.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baeldung.skipselectbeforeinsert.model.Task;

import io.hypersistence.utils.spring.repository.BaseJpaRepository;

@Repository
public interface TaskJpaRepository extends JpaRepository<Task, Integer>, BaseJpaRepository<Task, Integer> {
}
