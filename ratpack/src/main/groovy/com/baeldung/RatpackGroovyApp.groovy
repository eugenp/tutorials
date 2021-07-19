package com.baeldung;

public class RatpackGroovyApp {

    public static void main(String[] args) {
        File file = new File("src/main/groovy/com/baeldung/Ratpack.groovy");
        def shell = new GroovyShell()  
        shell.evaluate(file)
    }
    
}

