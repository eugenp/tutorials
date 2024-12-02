package com.baeldung.junit.log;

import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;

/**
 * In memory slf4j appender<br/>
 * Convenient appender to be able to check slf4j invocations
 */
public class MemoryAppender extends ListAppender<ILoggingEvent> {

    public void reset() {
        this.list.clear();
    }

    public boolean contains(String string, Level level) {
        return this.list.stream()
            .anyMatch(event -> event.toString()
                .contains(string) && event.getLevel()
                .equals(level));
    }

    public int countEventsForLogger(String loggerName) {
        return (int) this.list.stream()
            .filter(event -> event.getLoggerName()
                .contains(loggerName))
            .count();
    }

    public List<ILoggingEvent> search(String string) {
        return this.list.stream()
            .filter(event -> event.toString()
                .contains(string))
            .collect(Collectors.toList());
    }

    public List<ILoggingEvent> search(String string, Level level) {
        return this.list.stream()
            .filter(event -> event.toString()
                .contains(string) && event.getLevel()
                .equals(level))
            .collect(Collectors.toList());
    }

    public int getSize() {
        return this.list.size();
    }

    public List<ILoggingEvent> getLoggedEvents() {
        return Collections.unmodifiableList(this.list);
    }

    public boolean containsPattern(Pattern pattern, Level level) {
        return this.list.stream()
            .filter(event -> event.getLevel().equals(level))
            .anyMatch(event -> pattern.matcher(event.getFormattedMessage()).matches());
    }

    public boolean containsPatterns(List<Pattern> patternList, Level level) {
        return patternList.stream()
            .allMatch(pattern -> containsPattern(pattern, level));
    }
}
