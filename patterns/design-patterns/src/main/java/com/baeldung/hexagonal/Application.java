package com.baeldung.hexagonal;

public class Application {

    Writer writer;

    Application(Writer writer) {
        this.writer = writer;
    }

    public void write(String text) {
        writer.write(text);
    }
}
