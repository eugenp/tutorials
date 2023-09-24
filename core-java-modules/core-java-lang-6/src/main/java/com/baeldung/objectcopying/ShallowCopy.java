package com.baeldung.objectcopying;

import java.util.List;

public class ShallowCopy {

    public static class Bookshelf {
        private int shelves;
        private List<Book> books;

        public Bookshelf(int shelves, List<Book> books) {
            this.shelves = shelves;
            this.books = books;
        }

        public Bookshelf(Bookshelf bookshelf) {
            this.shelves = bookshelf.shelves;
            this.books = bookshelf.books;
        }

        public int getShelves() {
            return shelves;
        }

        public List<Book> getBooks() {
            return books;
        }

        @Override
        public String toString() {
            return "Bookshelf{" + "shelves=" + shelves + ", books=" + books + '}';
        }
    }

    public static class Book {
        private String title;
        private String author;

        public Book(String title, String author) {
            this.title = title;
            this.author = author;
        }

        public Book(Book book) {
            this.title = book.title;
            this.author = book.author;
        }

        @Override
        public String toString() {
            return "Book{" + "title='" + title + '\'' + ", author='" + author + '\'' + '}';
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }
    }

}
