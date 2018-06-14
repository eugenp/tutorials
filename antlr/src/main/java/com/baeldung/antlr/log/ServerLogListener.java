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


    private List<LogEntry> errors = new ArrayList<>();
    private List<LogEntry> debugs = new ArrayList<>();
    private List<LogEntry> infos = new ArrayList<>();

    private LogEntry currentLogEntry;

    @Override
    public void enterLogEntry(ServerLogParser.LogEntryContext ctx) {
        this.currentLogEntry = new LogEntry();
    }

    @Override
    public void exitLogEntry(ServerLogParser.LogEntryContext ctx) {
        switch (currentLogEntry.getLevel()) {
            case INFO:
                infos.add(currentLogEntry);
                break;
            case DEBUG:
                debugs.add(currentLogEntry);
                break;
            case ERROR:
                errors.add(currentLogEntry);
                break;
        }
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

    public List<LogEntry> getErrors() {
        return Collections.unmodifiableList(errors);
    }

    public List<LogEntry> getDebugs() {
        return Collections.unmodifiableList(debugs);
    }

    public List<LogEntry> getInfos() {
        return Collections.unmodifiableList(infos);
    }
}
