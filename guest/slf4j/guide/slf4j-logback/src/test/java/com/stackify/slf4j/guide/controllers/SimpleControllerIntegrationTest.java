package com.stackify.slf4j.guide.controllers;

import static org.powermock.api.mockito.PowerMockito.doNothing;
import static org.powermock.api.mockito.PowerMockito.spy;

import java.util.Collections;

import org.assertj.core.api.Condition;
import org.assertj.core.api.SoftAssertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.MDC;

import com.stackify.slf4j.guide.utils.ListAppender;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;

@RunWith(PowerMockRunner.class)
@PrepareForTest(MDC.class)
public class SimpleControllerIntegrationTest {

    SimpleController controller = new SimpleController();

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
                return clientId.equals(entry.getMDCPropertyMap()
                    .get("clientId"));
            })
            .haveAtLeastOne(eventContains("Client id-1234 has made a request", Level.INFO))
            .haveAtLeastOne(eventContains("Starting request", Level.INFO))
            .haveAtLeastOne(eventContains("Finished request", Level.INFO));
        errorCollector.assertThat(output)
            .isEqualTo("finished");
        errorCollector.assertAll();
    }

    @Test
    public void whenMarkerRequestMade_thenMessagesWithMarkerLogged() throws Exception {
        String output = controller.clientMarkerRequest();

        SoftAssertions errorCollector = new SoftAssertions();
        errorCollector.assertThat(ListAppender.getEvents())
            .haveAtLeastOne(eventContains("client has made a request", Level.INFO))
            .haveAtLeastOne(eventContains("Starting request", Level.INFO, "MYMARKER"))
            .haveAtLeastOne(eventContains("Finished request", Level.DEBUG, "MYMARKER"))
            .haveExactly(2, eventContains(null, null, "MYMARKER"));
        errorCollector.assertThat(output)
            .isEqualTo("finished");
        errorCollector.assertAll();
    }

    @Test
    public void whenProfilerRequestMade_thenMessagesWithPerformanceLogged() throws Exception {
        String output = controller.clientProfilerRequest();

        SoftAssertions errorCollector = new SoftAssertions();
        errorCollector.assertThat(ListAppender.getEvents())
            .haveAtLeastOne(eventContains("client has made a request", Level.INFO))
            .haveAtLeastOne(eventContains("Profiler [MYPROFILER]", Level.DEBUG));
        errorCollector.assertThat(output)
            .isEqualTo("finished");
        errorCollector.assertAll();
    }

    @Test
    public void whenEventRequestMade_thenMessagesWithEventLogged() throws Exception {
        String sender = "sender";
        String receiver = "receiver";
        String output = controller.clientEventRequest(sender, receiver);

        SoftAssertions errorCollector = new SoftAssertions();
        errorCollector.assertThat(ListAppender.getEvents())
            .haveAtLeastOne(eventContains("sending from sender to receiver", Level.INFO))
            .haveAtLeastOne(eventContains("<object class=\"java.util.HashMap\">", Level.INFO))
            .haveAtLeastOne(eventContains("<string>sender</string>", Level.INFO))
            .haveAtLeastOne(eventContains("<string>receiver</string>", Level.INFO));
        errorCollector.assertThat(output)
            .isEqualTo("finished");
        errorCollector.assertAll();
    }

    @Test
    public void givenESLocale_whenLocaleRequestMade_thenMessagesWithEventLogged() throws Exception {
        String locale = "es-ES";
        String output = controller.clientLocaleRequest(locale);

        SoftAssertions errorCollector = new SoftAssertions();
        errorCollector.assertThat(ListAppender.getEvents())
            .haveAtLeastOne(eventContains("El cliente parametrizedClientId ha realizado una solicitud usando locale es-ES", Level.INFO))
            .haveAtLeastOne(eventContains("Solicitud iniciada", Level.DEBUG))
            .haveAtLeastOne(eventContains("Solicitud finalizada", Level.INFO));
        errorCollector.assertThat(output)
            .isEqualTo("finished");
        errorCollector.assertAll();
    }

    @Test
    public void givenENLocale_whenLocaleRequestMade_thenMessagesWithEventLogged() throws Exception {
        String locale = "en-US";
        String output = controller.clientLocaleRequest(locale);

        SoftAssertions errorCollector = new SoftAssertions();
        errorCollector.assertThat(ListAppender.getEvents())
            .haveAtLeastOne(eventContains("Client parametrizedClientId has made a request using locale en-US", Level.INFO))
            .haveAtLeastOne(eventContains("Request started", Level.DEBUG))
            .haveAtLeastOne(eventContains("Request finished", Level.INFO));
        errorCollector.assertThat(output)
            .isEqualTo("finished");
        errorCollector.assertAll();
    }

    private Condition<ILoggingEvent> eventContains(String substring, Level level) {
        return eventContains(substring, level, null);
    }

    private Condition<ILoggingEvent> eventOfLevel(Level level) {
        return eventContains(null, level, null);
    }

    private Condition<ILoggingEvent> eventContains(String substring, Level level, String markerName) {

        return new Condition<ILoggingEvent>(entry -> (substring == null || (entry.getFormattedMessage() != null && entry.getFormattedMessage()
            .contains(substring))) && (level == null || level.equals(entry.getLevel())) && (markerName == null || (entry.getMarker() != null
                && markerName.equals(entry.getMarker()
                    .getName()))),
            String.format("entry with message '%s', level %s and marker %s", substring, level, markerName));
    }
}
