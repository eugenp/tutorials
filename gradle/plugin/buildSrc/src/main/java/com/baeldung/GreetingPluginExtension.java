package com.baeldung;

public class GreetingPluginExtension {
    private String greeter = "Baeldung";
    private String message = "Message from Plugin!";

    public String getGreeter() {
        return greeter;
    }

    public void setGreeter(String greeter) {
        this.greeter = greeter;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
