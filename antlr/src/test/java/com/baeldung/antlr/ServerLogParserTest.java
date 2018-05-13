package com.baeldung.antlr;

import com.baeldung.antlr.log.ServerLogWalker;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import com.baeldung.antlr.log.model.LogLevel;
import com.baeldung.antlr.log.model.LogEntry;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.Test;

import java.time.LocalDateTime;


public class ServerLogParserTest {

    @Test
    public void whenLogContainsOneErrorLogEntry_thenOneErrorIsReturned() throws Exception {
        ServerLogLexer serverLogLexer = new ServerLogLexer(TestingUtils.loadResourceAsCharStream("sample.log"));
        CommonTokenStream tokens = new CommonTokenStream( serverLogLexer );
        ServerLogParser logParser = new ServerLogParser(tokens);
        ParseTreeWalker walker = new ParseTreeWalker();
        ServerLogWalker logWalker = new ServerLogWalker();
        walker.walk(logWalker, logParser.log());
        LogEntry logEntry = logWalker.getErrors().get(0);

        assertThat(logWalker.getErrors().size(), is(1));
        assertThat(logEntry.getLevel(), is(LogLevel.ERROR));
        assertThat(logEntry.getMessage(), is("Bad thing happened"));
        assertThat(logEntry.getDateTime(), is(LocalDateTime.of(2018,5,5,14,20,24)));


    }
}
