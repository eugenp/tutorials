package com.baeldung.statickeyword;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Logger;

public class Library {
    public static final int MAX_BOOKS = 10000;
    private static final Logger LOGGER = Logger.getLogger(Library.class.getName());
    public static int totalBooks;
    public static List<String> lines;

    static {
        totalBooks = 0;
    }

    static {
        try {
            lines = Files.readAllLines(Paths.get("file.txt"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private final String name;
    private final int books;

    public Library(String name, int books) {
        this.name = name;
        this.books = books;
        totalBooks += books;
    }

    public static int getTotalBooks() {
        return totalBooks;
    }

    public static void printLibraryDetails(Library library) {
        LOGGER.info("Library Name: " + library.name);
        LOGGER.info("Number of Books: " + library.books);
    }

    public static String getLibraryInformation(Library library) {
        return library.getName() + " has " + library.getBooks() + " books.";
    }

    public static synchronized void addBooks(int count) {
        totalBooks += count;
    }

    public String getName() {
        return name;
    }

    public int getBooks() {
        return books;
    }

    public static class LibraryStatistics {
        public static int getAverageBooks(Library[] libraries) {
            int total = 0;
            for (Library library : libraries) {
                total += library.books;
            }
            return libraries.length > 0 ? total / libraries.length : 0;
        }
    }

    public static class Book {
        private final String title;
        private final String author;

        public Book(String title, String author) {
            this.title = title;
            this.author = author;
        }

        public void displayBookDetails() {
            LOGGER.info("Book Title: " + title);
            LOGGER.info("Book Author: " + author);
        }
    }

    public record BookRecord(String title, String author) {
    }
}