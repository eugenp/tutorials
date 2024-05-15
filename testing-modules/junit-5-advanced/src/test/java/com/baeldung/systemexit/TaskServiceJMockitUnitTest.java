package com.baeldung.systemexit;

import mockit.Mock;
import mockit.MockUp;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

public class TaskServiceJMockitUnitTest {

    @Test
    public void givenDAOThrowsException_whenSaveTaskIsCalled_thenSystemExitIsCalled() throws Exception {

        new MockUp<System>() {
            @Mock
            public void exit(int value) {
                throw new RuntimeException(String.valueOf(value));
            }
        };

        Task task = new Task("test");
        TaskDAO taskDAO = mock(TaskDAO.class);
        TaskService service = new TaskService(taskDAO);
        try {
            doThrow(new NullPointerException()).when(taskDAO).save(task);
            service.saveTask(task);
        } catch (RuntimeException runtimeException) {
            Assertions.assertEquals("1", runtimeException.getMessage());
        }
    }
}
