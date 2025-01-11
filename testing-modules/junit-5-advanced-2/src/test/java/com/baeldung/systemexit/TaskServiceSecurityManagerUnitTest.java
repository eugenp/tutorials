package com.baeldung.systemexit;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import java.security.Permission;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TaskServiceSecurityManagerUnitTest {

    @BeforeEach
    void setUp() {
        System.setSecurityManager(new NoExitSecurityManager());
    }

    @Test
    void givenDAOThrowsException_whenSaveTaskIsCalled_thenSystemExitIsCalled() throws Exception {
        Task task = new Task("test");
        TaskDAO taskDAO = mock(TaskDAO.class);
        TaskService service = new TaskService(taskDAO);
        try {
            doThrow(new NullPointerException()).when(taskDAO).save(task);
            service.saveTask(task);
        } catch (RuntimeException e) {
            Assertions.assertEquals("1", e.getMessage());
        }
    }

    private static class NoExitSecurityManager extends SecurityManager {
        @Override
        public void checkPermission(Permission perm) {
        }

        @Override
        public void checkExit(int status) {
            super.checkExit(status);
            throw new RuntimeException(String.valueOf(status));
        }
    }
}