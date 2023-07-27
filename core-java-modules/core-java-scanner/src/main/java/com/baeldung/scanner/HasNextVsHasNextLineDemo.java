package com.baeldung.scanner;

import lombok.extern.log4j.Log4j;
import org.apache.log4j.LogManager;
import org.apache.log4j.PropertyConfigurator;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Scanner;

@Log4j
public class HasNextVsHasNextLineDemo {
    private static final String LINE = "----------------------------";
    private static final String END_LINE = "--------OUTPUT--END---------\n";


    private static final String INPUT = new StringBuilder()
        .append("magic\tproject\n")
        .append("     database: oracle\n")
        .append("dependencies:\n")
        .append("spring:foo:bar\n")
        .append("\n").toString();

    private static void hasNextBasic() {
        printHeader("hasNext() Basic");
        Scanner scanner = new Scanner(INPUT);
        while (scanner.hasNext()) {
            log.info(scanner.next());
        }
        log.info(END_LINE);
        scanner.close();
    }

    private static void hasNextWithDelimiter() {
        printHeader("hasNext() with delimiter");
        Scanner scanner = new Scanner(INPUT);
        while (scanner.hasNext()) {
            String token = scanner.next();
            if ("dependencies:".equals(token)) {
                scanner.useDelimiter(":");
            }
            log.info(token);
        }
        log.info(END_LINE);
        scanner.close();
    }

    private static void hasNextWithDelimiterFixed() {
        printHeader("hasNext() with delimiter FIX");
        Scanner scanner = new Scanner(INPUT);
        while (scanner.hasNext()) {
            String token = scanner.next();
            if ("dependencies:".equals(token)) {
                scanner.useDelimiter(":|\\s+");
            }
            log.info(token);
        }
        log.info(END_LINE);
        scanner.close();
    }

    private static void addLineNumber() {
        printHeader("add line number by hasNextLine() ");
        Scanner scanner = new Scanner(INPUT);
        int i = 0;
        while (scanner.hasNextLine()) {
            log.info(String.format("%d|%s", ++i, scanner.nextLine()));
        }
        log.info(END_LINE);
        scanner.close();
    }

    private static void printHeader(String title) {
        log.info(LINE);
        log.info(title);
        log.info(LINE);
    }

    public static void main(String[] args) throws IOException {
        setLogger();
        hasNextBasic();
        hasNextWithDelimiter();
        hasNextWithDelimiterFixed();
        addLineNumber();
    }

    //overwrite the logger config
    private static void setLogger() throws IOException {
        InputStream is = HasNextVsHasNextLineDemo.class.getResourceAsStream("/scanner/log4j.properties");
        Properties props = new Properties();
        props.load(is);
        LogManager.resetConfiguration();
        PropertyConfigurator.configure(props);
    }
}