package com.baeldung.inheritancecomposition.model;

/**
 * 女服务员
 * @author zn.wang
 */
public class Waitress extends Person {
    
    public Waitress(String name, String email, int age) {
        super(name, email, age);
    }
    
    public String serveStarter(String starter) {
        return "Serving a " + starter;
    }
    
    public String serveMainCourse(String mainCourse) {
        return "Serving a " + mainCourse;
    }
    
    public String serveDessert(String dessert) {
        return "Serving a " + dessert;
    }
}
