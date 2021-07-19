package com.stackify.slf4j.guide.controllers;

import static org.powermock.api.mockito.PowerMockito.doNothing;
import static org.powermock.api.mockito.PowerMockito.spy;

import java.util.Collections;

import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;
import org.assertj.core.api.Condition;
import org.assertj.core.api.SoftAssertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.MDC;

import com.stackify.slf4j.guide.utils.ListAppender;

@RunWith(PowerMockRunner.class)
@PrepareForTest(MDC.class)
public class SimpleControllerIntegrationTest {

    private SimpleController controller = new SimpleController();

    @Before
    public void clearLogList() {
        ListAppender.clearEventList();
    }

    @Test
    public void whenSimpleRequestMade_thenAllRegularMessagesLogged() {
        String output = controller.processList(Collections.emptyList());

        SoftAssertions errorCollector = new SoftAssertions();
        errorCollector.assertThat(ListAppender.getEvents())
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
        // We avoid cleaning the context so tht we can check it afterwards
        spy(MDC.class);
        doNothing().when(MDC.class);
        MDC.clear();
        String clientId = "id-1234";

        String output = controller.clientMDCRequest(clientId);

        SoftAssertions errorCollector = new SoftAssertions();
        errorCollector.assertThat(ListAppender.getEvents())
            .allMatch(entry -> {
                return clientId.equals(entry.getMDC("clientId"));
            })
            .haveAtLeastOne(eventContains("Client id-1234 has made a request", Level.INFO))
            .haveAtLeastOne(eventContains("Starting request", Level.INFO))
            .haveAtLeastOne(eventContains("Finished request", Level.INFO));
        errorCollector.assertThat(output)
            .isEqualTo("finished");
        errorCollector.assertAll();

    }

    private Condition<LoggingEvent> eventOfLevel(Level level) {
        return eventContains(null, level);
    }

    private Condition<LoggingEvent> eventContains(String substring, Level level) {

        return new Condition<LoggingEvent>(entry -> (substring == null || (entry.getRenderedMessage() != null && entry.getRenderedMessage()
            .contains(substring))) && (level == null || level.equals(entry.getLevel())), String.format("entry with message '%s', level %s", substring, level));
    }
}
