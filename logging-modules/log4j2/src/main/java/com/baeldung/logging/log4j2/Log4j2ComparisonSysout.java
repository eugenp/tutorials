package com.baeldung.logging.log4j2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

public class Log4j2ComparisonSysout {

    public static void main(String[] args) throws FileNotFoundException {
        PrintStream outStream = new PrintStream(new File("outFile.txt"));
        System.setOut(outStream);
        System.out.println("This is a baeldung article");
        
        PrintStream errStream = new PrintStream(new File("errFile.txt"));
        System.setErr(errStream);
        System.err.println("This is a baeldung article error");
    }
}
