package com.baeldung.configuremockbean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.annotation.PostConstruct;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = ConfigureMockBeanApplication.class)
@ActiveProfiles("Dev")
public class ProfileBasedMockBeanConfigUnitTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserController userController;

    @Test
    void whenDevProfileActive_thenReturnUserName() {
        assertEquals("Alice Brown", userController.getUserName(4L));
        verify(userService).getUserName(4L);
    }
}

@Configuration
@Profile("Dev")
class DevProfileTestConfig {

    @MockBean
    UserService userService;

    @PostConstruct
    public void initMock() {
        when(userService.getUserName(4L)).thenReturn("Alice Brown");
    }
}
