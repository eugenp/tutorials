package com.baeldung.automq;

import org.awaitility.Awaitility;
import org.awaitility.Durations;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.concurrent.TimeUnit;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(OutputCaptureExtension.class)
@Import(TestcontainersConfiguration.class)
class UserOnboardingInitiatedListenerLiveTest {

    @Autowired
    private KafkaTemplate<String, User> kafkaTemplate;

    @Value("${com.baeldung.topic.onboarding-initiated}")
    private String onboardingInitiatedTopic;

    @BeforeAll
    void setUp(CapturedOutput capturedOutput) {
        String expectedLog = "partitions assigned";
        Awaitility
            .await()
            .atMost(Durations.ONE_MINUTE)
            .pollDelay(Durations.ONE_SECOND)
            .until(() -> capturedOutput.getAll().contains(expectedLog));
    }

    @Test
    void whenMessagePublishedToTopic_thenProcessedByListener(CapturedOutput capturedOutput) {
        User user = new User("test@baeldung.com");
        kafkaTemplate.send(onboardingInitiatedTopic, user);

        String expectedConsumerLog = String.format("Dispatching user account confirmation email to %s", user.email());
        Awaitility
            .await()
            .atMost(1, TimeUnit.SECONDS)
            .until(() -> capturedOutput.getAll().contains(expectedConsumerLog));
    }

}