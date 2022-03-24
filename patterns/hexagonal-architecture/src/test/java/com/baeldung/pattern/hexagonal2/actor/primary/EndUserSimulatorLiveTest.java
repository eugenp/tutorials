package com.baeldung.pattern.hexagonal2.actor.primary;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;

@ExtendWith({ SpringExtension.class, OutputCaptureExtension.class })
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class EndUserSimulatorLiveTest {

    @Autowired
    private EndUserSimulator endUserSimulator;

    private ListAppender listAppender;

    private static Logger logger;
    private static ListAppender<ILoggingEvent> loggerAppender;

    @BeforeEach
    public void setup() {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        logger = context.getLogger(EndUserSimulator.class.getSimpleName());
        loggerAppender = new ListAppender<>();
        loggerAppender.start();
        logger.addAppender(loggerAppender);
        logger.setLevel(Level.ALL);
        loggerAppender.clearAllFilters();
        loggerAppender.list.clear();
    }

    @Test
    public void whenUserIsCreatedByClient_thenLogShowsNewlyCreatedUser() {
        endUserSimulator.createUser();
        String expectedLogMessage = "Successfully created user: {\"id\":\"f52c9e91-4132-4318-9e7a-79517421a510\",\"userName\":\"my.new@user.com\"}";
        assertThat(loggerAppender.list).hasSize(1);
        String actualLogMessage = loggerAppender.list.get(0)
            .getMessage();
        assertThat(actualLogMessage).isEqualTo(expectedLogMessage);
    }

    @Test
    public void whenUsersAreRequestedByClient_thenLogShowsListOfUsers() {
        endUserSimulator.requestUsers();
        String expectedLogMessage = "List all users: [{\"id\":\"f52c9e91-4132-4318-9e7a-79517421a510\",\"userName\":\"my.new@user.com\"}]";
        assertThat(loggerAppender.list).hasSize(1);
        String actualLogMessage = loggerAppender.list.get(0)
            .getMessage();
        assertThat(actualLogMessage).isEqualTo(expectedLogMessage);
    }

    @Test
    public void whenUserIsDeleted_thenLogShowsDeletionSuccessMessage() {
        endUserSimulator.deleteUser();
        String expectedLogMessage = "User successfully deleted.";
        assertThat(loggerAppender.list).hasSize(1);
        String actualLogMessage = loggerAppender.list.get(0)
            .getMessage();
        assertThat(actualLogMessage).isEqualTo(expectedLogMessage);
    }
}
