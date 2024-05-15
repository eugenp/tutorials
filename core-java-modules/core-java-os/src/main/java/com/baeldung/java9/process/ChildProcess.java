package com.baeldung.java9.process;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChildProcess {

    public static void main(String[] args) {
        @SuppressWarnings("resource")
        Scanner input = new Scanner(System.in);
        Logger log = Logger.getLogger(ChildProcess.class.getName());
        log.log(Level.INFO, input.nextLine());
    }
}
