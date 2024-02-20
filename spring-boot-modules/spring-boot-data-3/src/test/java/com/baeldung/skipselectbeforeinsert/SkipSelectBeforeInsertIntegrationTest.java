package com.baeldung.skipselectbeforeinsert;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import com.baeldung.skipselectbeforeinsert.model.PersistableTask;
import com.baeldung.skipselectbeforeinsert.model.Task;
import com.baeldung.skipselectbeforeinsert.model.TaskWithGeneratedId;
import com.baeldung.skipselectbeforeinsert.repository.PersistableTaskRepository;
import com.baeldung.skipselectbeforeinsert.repository.TaskJpaRepository;
import com.baeldung.skipselectbeforeinsert.repository.TaskRepository;
import com.baeldung.skipselectbeforeinsert.repository.TaskWithGeneratedIdRepository;

@DataJpaTest
public class SkipSelectBeforeInsertIntegrationTest {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private TaskWithGeneratedIdRepository taskWithGeneratedIdRepository;
    @Autowired
    private PersistableTaskRepository persistableTaskRepository;
    @Autowired
    private TaskJpaRepository taskJpaRepository;

    @Test
    void givenRepository_whenSaveNewTaskWithPopulatedId_thenExtraSelectIsExpected() {
        Task task = new Task();
        task.setId(1);
        taskRepository.saveAndFlush(task);
    }

    @Test
    void givenRepository_whenSaveNewTaskWithGeneratedId_thenNoExtraSelectIsExpected() {
        TaskWithGeneratedId task = new TaskWithGeneratedId();
        TaskWithGeneratedId saved = taskWithGeneratedIdRepository.saveAndFlush(task);
        assertNotNull(saved.getId());
    }

    @Test
    void givenRepository_whenSaveNewPersistableTask_thenNoExtraSelectIsExpected() {
        PersistableTask persistableTask = new PersistableTask();
        persistableTask.setId(2);
        persistableTask.setNew(true);
        PersistableTask saved = persistableTaskRepository.saveAndFlush(persistableTask);
        assertEquals(2, saved.getId());
    }

    @Test
    void givenRepository_whenSaveNewPersistableTasksWithSameId_thenExceptionIsExpected() {
        PersistableTask persistableTask = new PersistableTask();
        persistableTask.setId(3);
        persistableTask.setNew(true);
        persistableTaskRepository.saveAndFlush(persistableTask);

        PersistableTask duplicateTask = new PersistableTask();
        duplicateTask.setId(3);
        duplicateTask.setNew(true);

        assertThrows(DataIntegrityViolationException.class,
          () -> persistableTaskRepository.saveAndFlush(duplicateTask));
    }

    @Test
    void givenRepository_whenPersistNewTaskUsingCustomPersistMethod_thenNoExtraSelectIsExpected() {
        Task task = new Task();
        task.setId(4);
        Task saved = taskRepository.persistAndFlush(task);

        assertEquals(4, saved.getId());
    }

    @Test
    void givenRepository_whenPersistNewTaskUsingPersist_thenNoExtraSelectIsExpected() {
        Task task = new Task();
        task.setId(5);
        Task saved = taskJpaRepository.persistAndFlush(task);

        assertEquals(5, saved.getId());
    }

    @Test
    void givenRepository_whenPersistTaskWithTheSameId_thenExceptionIsExpected() {
        Task task = new Task();
        task.setId(5);
        taskJpaRepository.persistAndFlush(task);

        Task secondTask = new Task();
        secondTask.setId(5);

        assertThrows(DataIntegrityViolationException.class,
          () ->  taskJpaRepository.persistAndFlush(secondTask));
    }

    @Test
    void givenRepository_whenPersistNewTaskUsingNativeQuery_thenNoExtraSelectIsExpected() {
        Task task = new Task();
        task.setId(6);
        taskRepository.insert(task);

        assertTrue(taskRepository.findById(6).isPresent());
    }
}
