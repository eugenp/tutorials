package com.baeldung.jdi;

public class JDIExampleDebuggee {

    public static void main(String[] args) {
        String jpda = "Java Platform Debugger Architecture";
        System.out.println("Hi Everyone, Welcome to " + jpda); //add a break point here

        String jdi = "Java Debug Interface"; //add a break point here and also stepping in here
        String text = "Today, we'll dive into " + jdi;
        System.out.println(text);
    }
    
}
