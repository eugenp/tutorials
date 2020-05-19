package com.baeldung.grep;

import java.io.File;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.unix4j.Unix4j;
import static org.unix4j.Unix4j.*;
import static org.junit.Assert.assertEquals;
import org.unix4j.line.Line;
import static org.unix4j.unix.Grep.*;
import static org.unix4j.unix.cut.CutOption.*;

public class GrepWithUnix4JIntegrationTest {

    private File fileToGrep;

    @Before
    public void init() {
        final String separator = File.separator;
        final String filePath = String.join(separator, new String[] { "src", "test", "resources", "dictionary.in" });
        fileToGrep = new File(filePath);
    }

    @Test
    public void whenGrepWithSimpleString_thenCorrect() {
        int expectedLineCount = 4;

        // grep "NINETEEN" dictionary.txt
        List<Line> lines = Unix4j.grep("NINETEEN", fileToGrep).toLineList();

        assertEquals(expectedLineCount, lines.size());
    }

    @Test
    public void whenInverseGrepWithSimpleString_thenCorrect() {
        int expectedLineCount = 178687;

        // grep -v "NINETEEN" dictionary.txt
        List<Line> lines = grep(Options.v, "NINETEEN", fileToGrep).toLineList();

        assertEquals(expectedLineCount, lines.size());
    }

    @Test
    public void whenGrepWithRegex_thenCorrect() {
        int expectedLineCount = 151;

        // grep -c ".*?NINE.*?" dictionary.txt
        String patternCount = grep(Options.c, ".*?NINE.*?", fileToGrep).cut(fields, ":", 1).toStringResult();

        assertEquals(expectedLineCount, Integer.parseInt(patternCount));
    }
}
