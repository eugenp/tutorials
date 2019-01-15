package com.baeldung.webclient.clientcredentials;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.webclient.clientcredentials.service.WebClientChonJob;
import com.baeldung.webclient.utils.ListAppender;

import ch.qos.logback.classic.spi.ILoggingEvent;

/**
 * 
 * Note: this Live test requires the Authorization Service and the Resource service located in the Baeldung/spring-security-oauth repo 
 * 
 * @author rozagerardo
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { ClientCredentialsOauthApplication.class })
public class OAuth2ClientCredentialsLiveTest {

    @Autowired
    WebClientChonJob service;

    @Before
    public void clearLogList() {
        ListAppender.clearEventList();
    }

    @Test
    public void givenFooWithNullId_whenProcessFoo_thenLogsWithDebugTrace() throws Exception {
        service.logResourceServiceResponse();
        
        Thread.sleep(3000);

        Collection<String> allLoggedEntries = ListAppender.getEvents()
            .stream()
            .map(ILoggingEvent::getFormattedMessage)
            .collect(Collectors.toList());
        assertThat(allLoggedEntries).anyMatch(entry -> entry.contains("We retrieved the following resource using Client Credentials Grant Type: {\"id\""));
    }

}
