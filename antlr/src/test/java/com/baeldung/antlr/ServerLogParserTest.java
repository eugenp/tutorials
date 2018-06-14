package com.baeldung.antlr;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import com.baeldung.antlr.log.ServerLogListener;
import com.baeldung.antlr.log.model.LogLevel;
import com.baeldung.antlr.log.model.LogEntry;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.Test;

import java.time.LocalDateTime;


public class ServerLogParserTest {

    @Test
    public void whenLogContainsOneErrorLogEntry_thenOneErrorIsReturned() throws Exception {
        String logLines = "2018-May-05 14:20:21 DEBUG entering awesome method\r\n" +
                "2018-May-05 14:20:24 ERROR Bad thing happened\r\n";
        ServerLogLexer serverLogLexer = new ServerLogLexer(CharStreams.fromString(logLines));
        CommonTokenStream tokens = new CommonTokenStream( serverLogLexer );
        ServerLogParser logParser = new ServerLogParser(tokens);
        ParseTreeWalker walker = new ParseTreeWalker();
        ServerLogListener logWalker = new ServerLogListener();
        walker.walk(logWalker, logParser.log());

        assertThat(logWalker.getEntries().size(), is(2));
        LogEntry error = logWalker.getEntries().get(1);
        assertThat(error.getLevel(), is(LogLevel.ERROR));
        assertThat(error.getMessage(), is("Bad thing happened"));
        assertThat(error.getDateTime(), is(LocalDateTime.of(2018,5,5,14,20,24)));


    }
}
