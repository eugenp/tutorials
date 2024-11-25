package com.baeldung.configuremockbean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.anyLong;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(classes = ConfigureMockBeanApplication.class)
public class MockBeanAnswersUnitTest {

    @MockBean
    private UserService mockUserService;

    @Autowired
    private UserController userController;

    @BeforeEach
    void setUp() {
        when(mockUserService.getUserName(anyLong())).thenAnswer(invocation -> {
            Long input = invocation.getArgument(0);
            if (input == 1L) {
                return "John Doe";
            } else if (input == 2L) {
                return "Jane Smith";
            } else {
                return "Bob Johnson";
            }
        });
    }

    @Test
    void whenDirectMockBean_thenReturnUserName() {
        assertEquals("John Doe", mockUserService.getUserName(1L));
        assertEquals("Jane Smith", mockUserService.getUserName(2L));
        assertEquals("Bob Johnson", mockUserService.getUserName(3L));

        verify(mockUserService).getUserName(1L);
        verify(mockUserService).getUserName(2L);
        verify(mockUserService).getUserName(3L);
    }
}
