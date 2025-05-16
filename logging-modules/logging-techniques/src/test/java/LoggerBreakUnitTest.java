import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LoggerBreakUnitTest {

    private Logger logger;
    private ListAppender<ILoggingEvent> listAppender;

    @BeforeEach
    void setUp() {
        logger = (Logger) LoggerFactory.getLogger(LoggerBreakUnitTest.class);
        logger.setLevel(Level.INFO);

        listAppender = new ListAppender<>();
        listAppender.start();
        logger.addAppender(listAppender);
    }

    @Test
    void givenLogMessage_whenEmptyLineLogged_thenLogContainsBlankEntry() {
        logger.info("Start process");
        logger.info("");

        List<ILoggingEvent> logs = listAppender.list;

        assertEquals(2, logs.size());
        assertEquals("Start process", logs.get(0)
            .getFormattedMessage());
        assertEquals("", logs.get(1)
            .getFormattedMessage());
    }

    @Test
    void givenLogMessage_whenUsingSystemLineSeparator_thenLogContainsPlatformSpecificLineBreak() {
        logger.info("Processing done{}", System.lineSeparator());

        List<ILoggingEvent> logs = listAppender.list;

        assertEquals(1, logs.size());
        assertTrue(logs.get(0)
            .getFormattedMessage()
            .endsWith(System.lineSeparator()));
    }

    @Test
    void givenLogMessage_whenLineBreakIsConcatenated_thenLogSplitsCorrectly() {
        logger.info("Processing done" + System.lineSeparator());

        List<ILoggingEvent> logs = listAppender.list;

        assertEquals(1, logs.size());
        assertTrue(logs.get(0)
            .getFormattedMessage()
            .endsWith(System.lineSeparator()));
    }

    @Test
    void givenLineSeparator_whenAppendedToLogMessages_thenLogEndsWithCorrectLineBreak() {
        String newline = System.getProperty("line.separator");

        logger.info("Processing started" + newline);
        logger.info("Processing ended");

        List<ILoggingEvent> logs = listAppender.list;

        assertEquals(2, logs.size());

        String firstMessage = logs.get(0)
            .getFormattedMessage();
        String secondMessage = logs.get(1)
            .getFormattedMessage();

        assertTrue(firstMessage.endsWith(newline), "First message should end with the system line separator");
        assertEquals("Processing ended", secondMessage);
    }
}

