package com.baeldung.inifileparser;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

import org.ini4j.InvalidFileFormatException;

public class IniFileParser {

    public static void main(String[] args) throws InvalidFileFormatException, IOException {
        System.out.println("Enter Complete File Path");
        Scanner scanInput = new Scanner(System.in);
        String completeFilePath = scanInput.nextLine();
        scanInput.close();
        IniFileParser fileParser = new IniFileParser();
        // Using Ini4j library
        fileParser.parseIniFileUsingIni4j(completeFilePath);
        // Using Apache Commons INIConfiguration
        fileParser.parseIniFileUsingApacheCommons(completeFilePath);
    }

    public Map<String, Map<String, String>> parseIniFileUsingIni4j(String filePath) throws InvalidFileFormatException, IOException {
        Ini4jParser ini4jParser = new Ini4jParser(new File(filePath));
        return ini4jParser.parseIniFile();
    }

    public Map<String, Map<String, String>> parseIniFileUsingApacheCommons(String filePath) {
        CommonsParser apacheCommonsParser = new CommonsParser(new File(filePath));
        return apacheCommonsParser.parseIniFile();
    }

}
