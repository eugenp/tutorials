package com.baeldung.configuremockbean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.annotation.PostConstruct;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

@SpringBootTest(classes = ConfigureMockBeanApplication.class)
@Import(InternalConfigMockBeanUnitTest.TestConfig.class)
public class InternalConfigMockBeanUnitTest {

    @TestConfiguration
    static class TestConfig {

        @MockBean
        UserService userService;

        @PostConstruct
        public void initMock() {
            when(userService.getUserName(3L)).thenReturn("Bob Johnson");
        }
    }

    @Autowired
    private UserService userService;

    @Autowired
    private UserController userController;

    @Test
    void whenConfiguredUserService_thenReturnUserName() {
        assertEquals("Bob Johnson", userController.getUserName(3L));
        verify(userService).getUserName(3L);
    }

}
