package com.baeldung.failureanalyzer;

import ch.qos.logback.classic.spi.ILoggingEvent;

import com.baeldung.failureanalyzer.utils.ListAppender;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.boot.SpringApplication;

import java.util.Collection;
import java.util.Properties;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class FailureAnalyzerAppIntegrationTest {

    private static final String EXPECTED_ANALYSIS_DESCRIPTION_TITLE = "Description:";
    private static final String EXPECTED_ANALYSIS_DESCRIPTION_CONTENT = "The bean myDAO could not be injected as com.baeldung.failureanalyzer.MyDAO because it is of type com.baeldung.failureanalyzer.MySecondDAO";
    private static final String EXPECTED_ANALYSIS_ACTION_TITLE = "Action:";
    private static final String EXPECTED_ANALYSIS_ACTION_CONTENT = "Consider creating a bean with name myDAO of type com.baeldung.failureanalyzer.MyDAO";

    @BeforeEach
    public void clearLogList() {
        ListAppender.clearEventList();
    }

    @Test
    public void givenBeanCreationErrorInContext_whenContextLoaded_thenFailureAnalyzerLogsReport() {
        try {
            Properties properties = new Properties();
            properties.setProperty("spring.profiles.active", "failureanalyzer");
            SpringApplication app = new SpringApplication(FailureAnalyzerApplication.class);
            app.setDefaultProperties(properties);
            app.run();
        } catch (BeanCreationException e) {
            Collection<String> allLoggedEntries = ListAppender.getEvents()
                .stream()
                .map(ILoggingEvent::getFormattedMessage)
                .collect(Collectors.toList());
            assertThat(allLoggedEntries).anyMatch(entry -> entry.contains(EXPECTED_ANALYSIS_DESCRIPTION_TITLE))
                .anyMatch(entry -> entry.contains(EXPECTED_ANALYSIS_DESCRIPTION_CONTENT))
                .anyMatch(entry -> entry.contains(EXPECTED_ANALYSIS_ACTION_TITLE))
                .anyMatch(entry -> entry.contains(EXPECTED_ANALYSIS_ACTION_CONTENT));
            return;
        }
        throw new IllegalStateException("Context load should be failing due to a BeanCreationException!");
    }

}
