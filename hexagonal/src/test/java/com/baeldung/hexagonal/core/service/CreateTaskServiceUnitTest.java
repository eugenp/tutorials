package com.baeldung.hexagonal.core.service;

import com.baeldung.hexagonal.core.domain.Todo;
import com.baeldung.hexagonal.core.port.required.IPersistToDoPort;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class CreateTaskServiceUnitTest {
    private IPersistToDoPort persistToDoPort;
    private CreateTaskService createTaskService;

    @Before
    public void setUp() {
        persistToDoPort = mock(IPersistToDoPort.class);
        createTaskService = new CreateTaskService(persistToDoPort);
    }

    @Test
    public void givenNameWhenCreateATodoItemThenSuccess() {
        doNothing().when(persistToDoPort).persistTodo(any(Todo.class));
        createTaskService.createTask("Test name");
        verify(persistToDoPort, times(1)).persistTodo(any(Todo.class));
    }
}
