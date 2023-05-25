package com.baeldung.ini;

import org.ini4j.Ini;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static java.util.stream.Collectors.toMap;

public class Ini4JParser {

    public static Map<String, Map<String, String>> parseIniFile(File fileToParse) throws IOException {
        Ini ini = new Ini(fileToParse);
        return ini.entrySet().stream()
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public static String readIniFileValue(File fileToParse, String section, String key) throws IOException {
        Ini ini = new Ini(fileToParse);
        return ini.get(section, key);
    }
}
