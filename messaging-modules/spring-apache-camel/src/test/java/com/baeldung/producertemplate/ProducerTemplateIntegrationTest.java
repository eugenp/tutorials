package com.baeldung.producertemplate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.baeldung.camel.producertemplate.ProducerTemplateApplication;
import com.baeldung.camel.producertemplate.ProducerTemplateController;

@CamelSpringBootTest
@SpringBootTest(classes = ProducerTemplateApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ProducerTemplateIntegrationTest {

    @Autowired
    private ProducerTemplateController producerTemplateController;

    @Autowired
    private ProducerTemplate producerTemplate;

    @Autowired
    private CamelContext camelContext;

    private static final String TEST_MESSAGE = "TestMessage";
    private static final Path OUTPUT_FILE = Paths.get("output/output.txt");

    @BeforeEach
    void setUp() throws IOException {

        if (Files.exists(OUTPUT_FILE)) {
            Files.delete(OUTPUT_FILE);
        }
    }

    @Test
    void givenMessage_whenSendingSimpleMessage_thenReturnsProcessedMessage() {
        String inputMessage = TEST_MESSAGE;
        String response = producerTemplateController.sendSimpleMessage(inputMessage);
        assertNotNull(response, "Response should not be null");
        assertEquals("Hello " + inputMessage, response);
    }

    @Test
    void givenMessage_whenSendingToFile_thenFileContainsMessage() throws IOException {
        String inputMessage = TEST_MESSAGE;
        String response = producerTemplateController.sendToFile(inputMessage);

        assertEquals("Message appended to output.txt", response);
        assertTrue(Files.exists(OUTPUT_FILE));
        String fileContent = Files.readString(OUTPUT_FILE);
        assertTrue(fileContent.contains(inputMessage));
    }

    @Test
    void givenMessage_whenSendingToBean_thenReturnsUppercaseMessage() {
        String inputMessage = TEST_MESSAGE;
        String response = producerTemplateController.sendToBean(inputMessage);
        assertNotNull(response);
        assertEquals("Bean processed " + inputMessage.toUpperCase(), response);
    }

}
