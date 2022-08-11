package com.baeldung.inifileparser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.configuration2.INIConfiguration;
import org.apache.commons.configuration2.SubnodeConfiguration;

public class CommonsParser {

    private File fileToParse;

    public CommonsParser(File iniFile) {
        this.fileToParse = iniFile;
    }

    public Map<String, Map<String, String>> parseIniFile() {
        INIConfiguration iniConfiguration = new INIConfiguration();
        Map<String, Map<String, String>> iniFileContents = new HashMap<>();
        Set<String> fileSections = new HashSet<>();

        try (FileReader fileReader = new FileReader(this.fileToParse); BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            iniConfiguration.read(bufferedReader);
            fileSections = iniConfiguration.getSections();
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (String section : fileSections) {
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

}
