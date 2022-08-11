package com.baeldung.inifileparser;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.ini4j.Ini;
import org.ini4j.InvalidFileFormatException;

public class Ini4jParser {

    private File fileToParse;

    public Ini4jParser(File iniFile) {
        this.fileToParse = iniFile;
    }

    public Map<String, Map<String, String>> parseIniFile() throws InvalidFileFormatException, IOException {
        Map<String, Map<String, String>> iniFileContents = new HashMap<>();
        Set<String> fileSections = new HashSet<>();
        Ini ini;
        // get All the sections
        ini = new Ini(this.fileToParse);
        fileSections = ini.keySet();
        // get the contents of sections
        for (String section : fileSections) {
            Map<String, String> subSectionMap = new HashMap<>();
            Ini.Section subSection = ini.get(section);
            Iterator<String> subSectionIterator = subSection.keySet()
                .iterator();
            while (subSectionIterator.hasNext()) {
                String key = subSectionIterator.next();
                String value = subSection.get(key);
                subSectionMap.put(key, value);
            }
            iniFileContents.put(section, subSectionMap);
        }
        return iniFileContents;
    }
}
