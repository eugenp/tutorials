package com.baeldung.jetbrainsxodus;

import jetbrains.exodus.entitystore.Entity;
import jetbrains.exodus.entitystore.EntityId;
import jetbrains.exodus.entitystore.PersistentEntityStore;
import jetbrains.exodus.entitystore.PersistentEntityStores;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class TaskEntityStoreRepository {
    private static final String DB_PATH = "db\\.myAppData";
    private static final String ENTITY_TYPE = "Task";

    public EntityId save(TaskEntity taskEntity) {
        try (PersistentEntityStore entityStore = openStore()) {

            AtomicReference<EntityId> idHolder = new AtomicReference<>();

            entityStore.executeInTransaction(txn -> {
                final Entity message = txn.newEntity(ENTITY_TYPE);
                message.setProperty("description", taskEntity.getDescription());
                message.setProperty("labels", taskEntity.getLabels());

                idHolder.set(message.getId());
            });

            return idHolder.get();
        }
    }

    private PersistentEntityStore openStore() {
        return PersistentEntityStores.newInstance(DB_PATH);
    }

    public TaskEntity findOne(EntityId taskId) {
        try (PersistentEntityStore entityStore = openStore()) {

            AtomicReference<TaskEntity> taskEntity = new AtomicReference<>();

            entityStore.executeInReadonlyTransaction(
              txn -> taskEntity.set(mapToTaskEntity(txn.getEntity(taskId))));

            return taskEntity.get();
        }
    }

    private TaskEntity mapToTaskEntity(Entity entity) {
        return new TaskEntity(entity.getProperty("description").toString(),
          entity.getProperty("labels").toString());
    }

    public List<TaskEntity> findAll() {
        try (PersistentEntityStore entityStore = openStore()) {
            List<TaskEntity> result = new ArrayList<>();

            entityStore.executeInReadonlyTransaction(txn -> txn.getAll(ENTITY_TYPE)
              .forEach(entity -> result.add(mapToTaskEntity(entity))));

            return result;
        }
    }

    public void deleteAll() {
        try (PersistentEntityStore entityStore = openStore()) {
            entityStore.clear();
        }
    }
}
