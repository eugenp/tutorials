package com.baeldung.clearconsolescreen;

import java.io.IOException;

public class ClearConsoleScreen {

    public static void clearWithANSICodes() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void clearWithBlankLines() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }

    public static void clearWithLinuxCommand() {
        try {
            new ProcessBuilder("clear")
                    .inheritIO()
                    .start()
                    .waitFor();
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) {

        System.out.println("This text appears first.");

        clearWithANSICodes();
        clearWithBlankLines();
        clearWithLinuxCommand();

        System.out.println("End of program output.");
    }
}
