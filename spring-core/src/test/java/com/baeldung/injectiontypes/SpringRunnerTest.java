package com.baeldung.injectiontypes;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.baeldung.injectiontypes.service.NotificationService;

@RunWith(SpringJUnit4ClassRunner.class)
public class SpringRunnerTest {
    @Mock
    private NotificationService notificationService;

    @Test
    public void whenCreatingNewInstance_ThenConstructor() {
        SpringRunner springRunner = new SpringRunner(notificationService);
        assertNotNull(springRunner.getNotificationService());
    }

    @Test
    public void whenCreatingNewInstance_ThenSetter() {
        SpringRunner springRunner = new SpringRunner();
        springRunner.setNotificationService(notificationService);
        assertNotNull(springRunner.getNotificationService());
    }
}
