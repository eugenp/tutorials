package com.baeldung;

import java.io.Console;

import org.joda.time.DateTime;

public class Application {
    public static void main(String[] args) {
        Console console = System.console();
        while(true){
            System.out.println("Type 'exit' to quit...\n");  
            
            DateTime jodaDate = new DateTime();
            String data = console.readLine("%s \n Type something to echo : ", jodaDate); 
            System.out.println("You entered : " + data);
            if("EXIT".toUpperCase().equals(data.toUpperCase())){
                return;
            }
        }
    }
}
