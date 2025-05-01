package com.baeldung.boot.jsp.coursewelcome;

public class CourseWelcome {

    public String greeting(String username) {
        return String.format("Hi %s, how are you doing?", username);
    }

    public static String staticWelcome(String courseName) {
        return String.format("Welcome to Bealdung's %s course", courseName);
    }

}