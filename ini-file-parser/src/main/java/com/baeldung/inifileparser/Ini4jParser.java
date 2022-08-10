package com.baeldung.inifileparser;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import org.ini4j.Ini;
import org.ini4j.InvalidFileFormatException;

public class Ini4jParser {

    private File fileToParse;

    public Ini4jParser(File iniFile) {
        this.fileToParse = iniFile;
    }

    public void parseIniFile() {
        System.out.println("Parsing Using Ini4j");
        try {
            Ini ini = new Ini(this.fileToParse);
            for (String section : ini.keySet()) {
                System.out.println("Section: " + section);
                Ini.Section subSection = ini.get(section);
                Iterator<String> subSectionIterator = subSection.keySet()
                    .iterator();
                while (subSectionIterator.hasNext()) {
                    String key = subSectionIterator.next();
                    System.out.println("KEY: " + key);
                    System.out.println("VALUE: " + subSection.get(key));
                }

            }
        } catch (InvalidFileFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
