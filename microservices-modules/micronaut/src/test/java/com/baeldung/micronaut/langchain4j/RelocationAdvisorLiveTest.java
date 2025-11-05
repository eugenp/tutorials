package com.baeldung.micronaut.langchain4j;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@MicronautTest(rebuildContext = true)
public class RelocationAdvisorLiveTest {

    Logger logger = LoggerFactory.getLogger(RelocationAdvisorLiveTest.class);

    @Inject
    RelocationAdvisor assistant;

    @Test
    void givenAdvisor_whenSendChatMessage_thenExpectedResponseShouldContainInformationAboutRomania() {
        String response = assistant.chat("Tell me about Romania");
        logger.info(response);
        Assertions.assertTrue(response.contains("Romania"));
    }

    @Test
    void givenAdvisor_whenSendChatMessage_thenExpectedResponseShouldContainInformationAboutSpain() {
        String response = assistant.chat("Tell me about Spain");
        logger.info(response);
        Assertions.assertTrue(response.contains("Spain"));
    }

    @Test
    void givenAdvisor_whenSendChatMessage_thenExpectedResponseShouldNotContainInformationAboutNorway() {
        String response = assistant.chat("Tell me about Norway");
        logger.info(response);
        Assertions.assertTrue(response.contains("I don't have information about Norway"));
    }
}
