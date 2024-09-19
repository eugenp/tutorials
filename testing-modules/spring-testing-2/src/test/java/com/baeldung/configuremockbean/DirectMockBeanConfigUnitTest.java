package com.baeldung.configuremockbean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(classes = ConfigureMockBeanApplication.class)
public class DirectMockBeanConfigUnitTest {

    @MockBean
    private UserService mockUserService;

    @Autowired
    private UserController userController;

    @Test
    void whenDirectMockBean_thenReturnUserName() {
        when(mockUserService.getUserName(1L)).thenReturn("John Doe");
        assertEquals("John Doe", userController.getUserName(1L));
        verify(mockUserService).getUserName(1L);
    }
}
