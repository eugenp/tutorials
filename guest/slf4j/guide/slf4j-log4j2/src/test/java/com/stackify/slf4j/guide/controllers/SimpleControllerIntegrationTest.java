package com.stackify.slf4j.guide.controllers;

import java.util.Collections;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.junit.LoggerContextRule;
import org.apache.logging.log4j.test.appender.ListAppender;
import org.assertj.core.api.Condition;
import org.assertj.core.api.SoftAssertions;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

public class SimpleControllerIntegrationTest {

    private static ListAppender appender;
    private SimpleController controller = new SimpleController();

    @ClassRule
    public static LoggerContextRule init = new LoggerContextRule("log4j2-test.xml");

    @BeforeClass
    public static void setupLogging() {
        appender = init.getListAppender("ListAppender");
    }

    @Before
    public void clearAppender() {
        appender.clear();
    }

    @Test
    public void whenSimpleRequestMade_thenAllRegularMessagesLogged() {
        String output = controller.processList(Collections.emptyList());

        SoftAssertions errorCollector = new SoftAssertions();
        errorCollector.assertThat(appender.getEvents())
            .haveAtLeastOne(eventContains("Client requested process the following list: []", Level.INFO))
            .haveAtLeastOne(eventContains("Starting process", Level.DEBUG))
            .haveAtLeastOne(eventContains("Finished processing", Level.INFO))
            .haveExactly(0, eventOfLevel(Level.ERROR));
        errorCollector.assertThat(output)
            .isEqualTo("done");
        errorCollector.assertAll();
    }

    @Test
    public void givenClientId_whenMDCRequestMade_thenMessagesWithClientIdLogged() throws Exception {
        String clientId = "id-1234";

        String output = controller.clientMDCRequest(clientId);

        SoftAssertions errorCollector = new SoftAssertions();
        errorCollector.assertThat(appender.getEvents())
            .allMatch(entry -> {
                return clientId.equals(entry.getContextData()
                    .getValue("clientId"));
            })
            .haveAtLeastOne(eventContains("Client id-1234 has made a request", Level.INFO))
            .haveAtLeastOne(eventContains("Starting request", Level.INFO))
            .haveAtLeastOne(eventContains("Finished request", Level.INFO));
        errorCollector.assertThat(output)
            .isEqualTo("finished");
        errorCollector.assertAll();
    }

    private Condition<LogEvent> eventOfLevel(Level level) {
        return eventContains(null, level);
    }

    private Condition<LogEvent> eventContains(String substring, Level level) {

        return new Condition<LogEvent>(entry -> (substring == null || (entry.getMessage()
            .getFormattedMessage() != null && entry.getMessage()
                .getFormattedMessage()
                .contains(substring)))
            && (level == null || level.equals(entry.getLevel())), String.format("entry with message '%s', level %s", substring, level));
    }
}
