package com.baeldung.configuremockbean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(classes = ConfigureMockBeanApplication.class)
public class ConfigureBeforeEachTestUnitTest {

    @MockBean
    private UserService mockUserService;

    @Autowired
    private UserController userController;

    @BeforeEach
    void setUp() {
        when(mockUserService.getUserName(1L)).thenReturn("John Doe");
    }

    @Test
    void whenParentContextConfigMockBean_thenReturnUserName() {
        assertEquals("John Doe", userController.getUserName(1L));
        verify(mockUserService).getUserName(1L);
    }
}
