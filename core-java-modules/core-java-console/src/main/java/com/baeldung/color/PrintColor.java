package com.baeldung.color;

import static org.fusesource.jansi.Ansi.ansi;

import org.fusesource.jansi.AnsiConsole;

public class PrintColor {
    
    public static void main(String[] args) {
        logColorUsingANSICodes();

        logColorUsingLogger();

        logColorUsingJANSI();
    }

    private static void logColorUsingANSICodes() {
        System.out.println("Here's some text");
        System.out.println("\u001B[31m" + "and now the text is red" + "\u001B[0m");
        System.out.println("and now back to the default");
    }

    private static void logColorUsingLogger() {
        ColorLogger colorLogger = new ColorLogger();
        colorLogger.logDebug("Some debug logging");
        colorLogger.logInfo("Some info logging");
        colorLogger.logError("Some error logging");
    }

    private static void logColorUsingJANSI() {
        AnsiConsole.systemInstall();

        System.out.println(ansi().fgRed().a("Some red text").fgYellow().a(" and some yellow text").reset());

        AnsiConsole.systemUninstall();
    }
}

/*
 * More ANSI codes:
 * 
 * Always conclude your logging with the ANSI reset code: "\u001B[0m"
 * 
 * In each case, replace # with the corresponding number:
 * 
 * 0 = black
 * 1 = red
 * 2 = green
 * 3 = yellow
 * 4 = blue
 * 5 = purple
 * 6 = cyan (light blue)
 * 7 = white
 * 
 * \u001B[3#m = color font
 * \u001B[4#m = color background
 * \u001B[1;3#m = bold font
 * \u001B[4;3#m = underlined font
 * \u001B[3;3#m = italics font (not widely supported, works in VS Code)
 */