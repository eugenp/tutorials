package com.baeldung.inifileparser;

import java.io.File;

public class IniFileParser {
    public static final String FILE_PATH = "C:/Baeldung/sample_config.ini";

    public static void main(String[] args) {
        File iniFile = new File(FILE_PATH);
        // Using Ini4j library
        Ini4jParser ini4jParser = new Ini4jParser(iniFile);
        ini4jParser.parseIniFile();
        // Using Apache Commons INIConfiguration
        CommonsParser apacheCommonsParser = new CommonsParser(iniFile);
        apacheCommonsParser.parseIniFile();

    }

}
