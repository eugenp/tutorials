package com.baeldung.solon.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import com.baeldung.solon.model.Task;
import com.baeldung.solon.persistence.TaskMapper;

class TaskServiceUnitTest {

    private TaskMapper taskMapper;
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        taskMapper = mock(TaskMapper.class);
        taskService = new TaskService();
        taskService.taskMapper = taskMapper;
    }

    @Test
    void givenPaddedTitle_whenCreatingTask_thenNormalizeTitleAndStartIncomplete() {
        Task task = taskService.create("  Learn Solon  ");

        assertEquals("Learn Solon", task.getTitle());
        assertFalse(task.isCompleted());
        verify(taskMapper).insert(task);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = { " ", "\t\n" })
    void givenBlankTitle_whenCreatingTask_thenRejectBeforePersistence(String title) {
        assertThrows(IllegalArgumentException.class, () -> taskService.create(title));
        verifyNoInteractions(taskMapper);
    }

    @Test
    void givenLongTitle_whenCreatingTask_thenRejectBeforePersistence() {
        assertThrows(IllegalArgumentException.class, () -> taskService.create("a".repeat(201)));
        verifyNoInteractions(taskMapper);
    }

    @Test
    void givenMissingTask_whenFindingById_thenReportMissingTask() {
        when(taskMapper.selectOneById(42L)).thenReturn(null);

        assertThrows(NoSuchElementException.class, () -> taskService.findById(42L));
    }
}
