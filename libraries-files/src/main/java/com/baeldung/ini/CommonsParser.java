package com.baeldung.ini;

import org.apache.commons.configuration2.INIConfiguration;
import org.apache.commons.configuration2.SubnodeConfiguration;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class CommonsParser {
    public static Map<String, Map<String, String>> parseIniFile(File fileToParse) throws IOException, ConfigurationException {
        Map<String, Map<String, String>> iniFileContents = new HashMap<>();

        INIConfiguration iniConfiguration = new INIConfiguration();
        try (FileReader fileReader = new FileReader(fileToParse)) {
            iniConfiguration.read(fileReader);
        }

        for (String section : iniConfiguration.getSections()) {
            Map<String, String> subSectionMap = new HashMap<>();
            SubnodeConfiguration confSection = iniConfiguration.getSection(section);
            Iterator<String> keyIterator = confSection.getKeys();
            while (keyIterator.hasNext()) {
                String key = keyIterator.next();
                String value = confSection.getProperty(key)
                        .toString();
                subSectionMap.put(key, value);
            }
            iniFileContents.put(section, subSectionMap);
        }
        return iniFileContents;
    }

    public static String readIniFileValue(File fileToParse, String section, String value) throws IOException, ConfigurationException {
        INIConfiguration iniConfiguration = new INIConfiguration();
        try (FileReader fileReader = new FileReader(fileToParse)) {
            iniConfiguration.read(fileReader);
        }

        return iniConfiguration.getSection(section).getProperty(value).toString();
    }
}
