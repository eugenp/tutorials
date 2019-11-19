package com.baeldung.evaluation.hexagon.application;

public class Application {
        boolean execute(String command){
                System.out.println(String.format("Executing command: '%s'"));
                System.out.println("Saving command output ...");

                System.out.println("Sending notifications ...");
                return true;
        }
}
