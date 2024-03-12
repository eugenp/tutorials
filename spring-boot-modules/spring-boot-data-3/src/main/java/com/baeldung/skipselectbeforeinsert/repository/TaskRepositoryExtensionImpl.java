package com.baeldung.skipselectbeforeinsert.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;

import com.baeldung.skipselectbeforeinsert.model.Task;

@Component
public class TaskRepositoryExtensionImpl implements TaskRepositoryExtension {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Task persistAndFlush(Task task) {
        entityManager.persist(task);
        entityManager.flush();
        return task;
    }
}
