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

@TestConfiguration
class TestConfig {

    @MockBean
    UserService userService;

    @PostConstruct
    public void initMock() {
        when(userService.getUserName(2L)).thenReturn("Jane Smith");
    }
}

@SpringBootTest(classes = ConfigureMockBeanApplication.class)
@Import(TestConfig.class)
class ConfigureMockBeanApplicationUnitTest {

    @Autowired
    private UserService mockUserService;

    @Autowired
    private UserController userController;

    @Test
    void whenConfiguredUserService_thenReturnUserName() {
        assertEquals("Jane Smith", userController.getUserName(2L));
        verify(mockUserService).getUserName(2L);
    }
}