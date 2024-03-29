package com.baeldung.deepvsshallowcopy;

public class GreeterServiceExample {
    public Greeting greetInYourLanguage(String language) {
        switch (language.toLowerCase()) {
            case "english":
                return new Greeting("Hello", new Language("English", "en"));
            case "spanish":
                return new Greeting("Hola", new Language("Spanish", "es"));
            case "xhosa":
                return new Greeting("Molo", new Language("Xhosa", "xh"));
            default:
                return null;
        }
    }
}
