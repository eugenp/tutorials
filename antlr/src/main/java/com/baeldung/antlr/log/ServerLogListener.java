package com.baeldung.antlr.log;

import com.baeldung.antlr.ServerLogBaseListener;
import com.baeldung.antlr.ServerLogParser;
import com.baeldung.antlr.log.model.LogLevel;
import com.baeldung.antlr.log.model.LogEntry;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class ServerLogListener extends ServerLogBaseListener {

    private static final DateTimeFormatter DEFAULT_DATETIME_FORMATTER
            = DateTimeFormatter.ofPattern("yyyy-MMM-dd HH:mm:ss", Locale.ENGLISH);


    private List<LogEntry> entries = new ArrayList<>();

    private LogEntry currentLogEntry;

    @Override
    public void enterLogEntry(ServerLogParser.LogEntryContext ctx) {
        this.currentLogEntry = new LogEntry();
    }

    @Override
    public void exitLogEntry(ServerLogParser.LogEntryContext ctx) {
        entries.add(currentLogEntry);
    }

    @Override
    public void enterServerTime(ServerLogParser.ServerTimeContext ctx) {
        currentLogEntry.setDateTime(LocalDateTime.parse(ctx.getText(), DEFAULT_DATETIME_FORMATTER));
    }

    @Override
    public void enterMessage(ServerLogParser.MessageContext ctx) {
        currentLogEntry.setMessage(ctx.getText());
    }

    @Override
    public void enterLevel(ServerLogParser.LevelContext ctx) {
        currentLogEntry.setLevel(LogLevel.valueOf(ctx.getText()));
    }

    public List<LogEntry> getEntries() {
        return Collections.unmodifiableList(entries);
    }
}
