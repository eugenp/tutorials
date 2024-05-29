package com.baeldung.internal.repo.sample;

public class GreeterServiceExample {
    public Greeting greetInYourLanguage(String language) {
        return switch (language.toLowerCase()) {
            case "english" -> new Greeting("Hello", new Language("English", "en"));
            case "spanish" -> new Greeting("Hola", new Language("Spanish", "es"));
            case "xhosa" -> new Greeting("Molo", new Language("Xhosa", "xh"));
            default -> null;
        };
    }
}
