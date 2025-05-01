package com.baeldung.boot.jsp.coursewelcome;

public class CourseWelcomeBean {

    private String username;
    private String courseName;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String greetingUser() {
        return String.format("Hi %s, how do you do?", username);
    }

    public String welcomeMsg() {
        return String.format("Welcome to Bealdung's %s course!", courseName);
    }
}