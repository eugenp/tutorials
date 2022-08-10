package com.baeldung.inifileparser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.configuration2.INIConfiguration;
import org.apache.commons.configuration2.SubnodeConfiguration;
import org.apache.commons.configuration2.ex.ConfigurationException;

public class CommonsParser {

    private File fileToParse;

    public CommonsParser(File iniFile) {
        this.fileToParse = iniFile;
    }

    public void parseIniFile() {
        System.out.println("Parsing Using Apache Commons Configuration library");
        INIConfiguration iniConfiguration = new INIConfiguration();

        try (FileReader fileReader = new FileReader(this.fileToParse); BufferedReader bufferedReader = new BufferedReader(fileReader)) {

            iniConfiguration.read(bufferedReader);
            Set<String> sections = iniConfiguration.getSections();

            for (String s : sections) {
                System.out.println("SECTION: " + s);
                SubnodeConfiguration section = iniConfiguration.getSection(s);
                Iterator<String> keyIterator = section.getKeys();
                while (keyIterator.hasNext()) {
                    String key = keyIterator.next();
                    System.out.println("KEY: " + key);
                    System.out.println("VALUE: " + section.getProperty(key));
                }
            }
        } catch (ConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
