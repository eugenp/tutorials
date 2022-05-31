package com.baeldung.spring.cloud.aws.sns;

import org.junit.Before;
import org.junit.Test;
import org.springframework.cloud.aws.messaging.endpoint.NotificationStatus;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;

public class SNSEndpointControllerUnitTest {

    SNSEndpointController snsEndpointController;

    @Before
    public void setUp() {
        snsEndpointController = new SNSEndpointController();
    }

    @Test
    public void whenReceivedNotificationInvoked_thenSuccess() {
        snsEndpointController.receiveNotification("Message", "Subject");
    }

    @Test
    public void whenConfirmUnsubscribeReturned_thenSuccess() {
        NotificationStatus notificationStatus = mock(NotificationStatus.class);
        doNothing().when(notificationStatus).confirmSubscription();
        snsEndpointController.confirmUnsubscribeMessage(notificationStatus);
    }

    @Test
    public void whenConfirmSubscriptionReturned_thenSuccess() {
        NotificationStatus notificationStatus = mock(NotificationStatus.class);
        doNothing().when(notificationStatus).confirmSubscription();
        snsEndpointController.confirmSubscriptionMessage(notificationStatus);
    }

}
