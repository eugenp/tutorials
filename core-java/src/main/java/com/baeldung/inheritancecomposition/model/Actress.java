package com.baeldung.inheritancecomposition.model;

/**
 * 女演员
 * @author zn.wang
 */
public class Actress extends Person {
    
    public Actress(String name, String email, int age) {
        super(name, email, age);
    }
    
    public String readScript(String movie) {
        return "Reading the script of " + movie;
    }
    
    public String performRole() {
        return "Performing a role";
    }
}
