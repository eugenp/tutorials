package com.baeldung.injectiontypes.services.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.baeldung.injectiontypes.service.NotificationService;

@RunWith(SpringJUnit4ClassRunner.class)
public class NotificationServiceImplTest {

    @Mock
    NotificationService notificationService;

    @Test
    public void whenSendingNotification_ThenTypeAndReceiverId() {
        String notificationType = "Alert";
        String receiverId = "1asf13asf";
        String expectedOutput = "Notification type: " + notificationType + ", receiverId: " + receiverId;
        when(notificationService.sendNotification(notificationType, receiverId)).thenReturn(expectedOutput);
        assertEquals(expectedOutput, notificationService.sendNotification(notificationType, receiverId));
    }
}
