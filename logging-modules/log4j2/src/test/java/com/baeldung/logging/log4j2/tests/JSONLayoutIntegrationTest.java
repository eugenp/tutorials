package com.baeldung.logging.log4j2.tests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.baeldung.logging.log4j2.Log4j2BaseIntegrationTest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.appender.WriterAppender;
import org.apache.logging.log4j.core.layout.JsonLayout;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.CharArrayWriter;
import java.io.Writer;


public class JSONLayoutIntegrationTest extends Log4j2BaseIntegrationTest {

    private Appender appender;
    private Logger logger;
    private final Writer writer = new CharArrayWriter();

    @BeforeEach
    public void setUp() {
        logger = LogManager.getLogger("CONSOLE_JSON_APPENDER");

        appender = WriterAppender.newBuilder()
          .setTarget(writer)
          .setLayout(JsonLayout.newBuilder().build())
          .setName("json_layout_for_testing")
          .build();
        appender.start();

        ((org.apache.logging.log4j.core.Logger) logger).addAppender(appender);
        ((org.apache.logging.log4j.core.Logger) logger).setLevel(org.apache.logging.log4j.Level.DEBUG);
    }

    @Test
    public void whenLogLayoutInJSON_thenOutputIsCorrectJSON() throws Exception {
        logger.debug("Debug message");

        writer.flush();
        assertTrue(isValidJSON(writer.toString()));
    }

    @AfterEach
    public void cleanup() {
        ((org.apache.logging.log4j.core.Logger) logger).removeAppender(appender);
    }

    private static boolean isValidJSON(String jsonInString) throws Exception {
        JsonNode jsonNode = new ObjectMapper().readTree(jsonInString);
        return jsonNode.get("message").asText().equals("Debug message");
    }

}
