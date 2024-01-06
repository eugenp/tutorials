package com.baeldung.antlr.log;

import com.baeldung.antlr.LogBaseListener;
import com.baeldung.antlr.LogParser;
import com.baeldung.antlr.log.model.LogLevel;
import com.baeldung.antlr.log.model.LogEntry;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class LogListener extends LogBaseListener {

    private static final DateTimeFormatter DEFAULT_DATETIME_FORMATTER
            = DateTimeFormatter.ofPattern("yyyy-MMM-dd HH:mm:ss", Locale.ENGLISH);

    private List<LogEntry> entries = new ArrayList<>();
    private LogEntry currentLogEntry;

    @Override
    public void enterEntry(LogParser.EntryContext ctx) {
        this.currentLogEntry = new LogEntry();
    }

    @Override
    public void exitEntry(LogParser.EntryContext ctx) {
        entries.add(currentLogEntry);
    }

    @Override
    public void enterTimestamp(LogParser.TimestampContext ctx) {
        currentLogEntry.setTimestamp(LocalDateTime.parse(ctx.getText(), DEFAULT_DATETIME_FORMATTER));
    }

    @Override
    public void enterMessage(LogParser.MessageContext ctx) {
        currentLogEntry.setMessage(ctx.getText());
    }

    @Override
    public void enterLevel(LogParser.LevelContext ctx) {
        currentLogEntry.setLevel(LogLevel.valueOf(ctx.getText()));
    }

    public List<LogEntry> getEntries() {
        return Collections.unmodifiableList(entries);
    }
}
