package com.baeldung.inifileparser;

import java.io.IOException;

import org.ini4j.InvalidFileFormatException;
import org.junit.Test;

public class TestIniFileParser {

    @Test
    public void testIni4jParser() throws InvalidFileFormatException, IOException {
        IniFileParser fileParser = new IniFileParser();
        System.out.println(fileParser.parseIniFileUsingIni4j("C:/Baeldung/sample_config.ini"));
    }

    @Test
    public void testApacheCommonsParser() throws InvalidFileFormatException, IOException {
        IniFileParser fileParser = new IniFileParser();
        System.out.println(fileParser.parseIniFileUsingIni4j("C:/Baeldung/sample_config.ini"));
    }
}
