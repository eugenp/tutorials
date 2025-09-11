package com.example.items;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayDeque;
import java.util.Queue;

public class App {
    public static void main(String[] args) throws Exception {
        // Read the book title from src/main/resources/data/book.txt
        String bookTitle;
        try (InputStream in = App.class.getResourceAsStream("/data/book.txt");
             BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
            bookTitle = br.readLine(); // first line is enough for our demo
            if (bookTitle == null) bookTitle = "Untitled Book";
        }

        Book book = new Book(bookTitle);
        book.use(EmptyContext.INSTANCE); // use book with empty context

        // Read doors (one per line) from src/main/resources/data/doors.txt
        Queue<String> doors = new ArrayDeque<>();
        try (InputStream in = App.class.getResourceAsStream("/data/doors.txt");
             BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
            br.lines().forEach(doors::add);
        }

        // Create and use a Key with a KeyContext
        Key key = new Key("MasterKey");
        KeyContext keyContext = new KeyContext(doors.peek(), doors); // use peek to get current door
        key.use(keyContext);
    }
}
