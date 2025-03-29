package com.baeldung.kafka.synchronous;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Import(TestcontainersConfiguration.class)
class SynchronousKafkaLiveTest {

    @Autowired
    private NotificationDispatchService notificationDispatchService;

    @Test
    void whenNotificationRequestSent_thenReplyReceived() throws ExecutionException, InterruptedException {
        NotificationDispatchRequest request = new NotificationDispatchRequest("test@it.com", "test-content");

        NotificationDispatchResponse response = notificationDispatchService.dispatch(request);

        assertThat(response.notificationId())
            .isNotNull();
    }

}