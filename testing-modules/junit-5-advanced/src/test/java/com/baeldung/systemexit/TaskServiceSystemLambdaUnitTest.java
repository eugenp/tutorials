package com.baeldung.systemexit;

import org.junit.jupiter.api.Assertions;
import org.junit.Test;

import static com.github.stefanbirkner.systemlambda.SystemLambda.catchSystemExit;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

public class TaskServiceSystemLambdaUnitTest {

    @Test
    public void givenDAOThrowsException_whenSaveTaskIsCalled_thenSystemExitIsCalled() throws Exception {
        int statusCode = catchSystemExit(() -> {
            Task task = new Task("test");
            TaskDAO taskDAO = mock(TaskDAO.class);
            TaskService service = new TaskService(taskDAO);
            doThrow(new NullPointerException()).when(taskDAO).save(task);
            service.saveTask(task);
        });
        Assertions.assertEquals(1, statusCode);
    }
}
