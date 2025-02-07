package com.baeldung.jetbrainsxodus;

import com.baeldung.jetbrainsxodus.TaskEntity;
import com.baeldung.jetbrainsxodus.TaskEntityStoreRepository;
import com.baeldung.jetbrainsxodus.TaskEnvironmentRepository;
import jetbrains.exodus.entitystore.EntityId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class JetbrainsXodusLiveTest {

    private TaskEnvironmentRepository repository = new TaskEnvironmentRepository();
    private TaskEntityStoreRepository taskEntityStoreRepository = new TaskEntityStoreRepository();

    @AfterEach
    void shutDown() {
        repository.deleteAll();
        taskEntityStoreRepository.deleteAll();
    }

    @Test
    void givenTaskEnvironmentRepository_whenGetTaskDescriptionById_thenExpectedDescriptionIsReturned() {

        String taskId = "123abc";
        String expectedDescription = "My task description";
        repository.save(taskId, expectedDescription);

        String taskDescription = repository.findOne(taskId);
        assertEquals(expectedDescription, taskDescription);
    }

    @Test
    void givenTaskEnvironmentRepository_whenGetAllTaskDescription_thenAllExpectedDescriptionsAreReturned() {

        List<String> taskDescriptions = new ArrayList<>();
        taskDescriptions.add("First task description");
        taskDescriptions.add("Second task description");
        taskDescriptions.add("Third task description");

        taskDescriptions.forEach(
            taskDescription -> repository.save(UUID.randomUUID().toString(),
              taskDescription)
        );

        Map<String, String> foundTaskDescriptions = repository.findAll();
        assertThat(foundTaskDescriptions)
          .hasSize(3)
          .values()
          .containsAll(taskDescriptions);
    }

    @Test
    void givenTaskEntityStoreRepository_whenGetTaskById_thenTaskWithExpectedDescriptionAndLabelsIsReturned() {

        String expectedDescription = "My task description";
        String expectedLabels = "done,checked";
        EntityId entityId = taskEntityStoreRepository.save(new TaskEntity(expectedDescription, expectedLabels));

        TaskEntity taskEntity = taskEntityStoreRepository.findOne(entityId);
        assertEquals(expectedDescription, taskEntity.getDescription());
        assertEquals(expectedLabels, taskEntity.getLabels());
    }

    @Test
    void givenTaskEntityStoreRepository_whenGetAllTasks_thenAllExpectedTasksArePresent() {

        String expectedLabels = "done,checked";

        List<String> taskDescriptions = new ArrayList<>();
        taskDescriptions.add("First task description");
        taskDescriptions.add("Second task description");
        taskDescriptions.add("Third task description");

        taskDescriptions.forEach(
          taskDescription -> taskEntityStoreRepository
            .save(new TaskEntity(taskDescription, expectedLabels)));

        List<TaskEntity> taskEntities = taskEntityStoreRepository.findAll();
        assertThat(taskEntities)
          .hasSize(3)
          .extracting("description")
          .containsAll(taskDescriptions);
    }
}
