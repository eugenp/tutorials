package com.baeldung.library.core;

public class Main {

    public static void main(String[] args) {
        Library library = new Library();
        library.addBook(new Book("The Lord of the Rings", "J.R.R. Tolkien"));
        library.addBook(new Book("The Hobbit", "J.R.R. Tolkien"));
        library.addBook(new Book("The Silmarillion", "J.R.R. Tolkien"));
        library.addBook(new Book("The Chronicles of Narnia", "C.S. Lewis"));
        library.addBook(new Book("The Lion, the Witch and the Wardrobe", "C.S. Lewis"));
        System.out.println("Welcome to our library!");
        System.out.println("We have the following books:");
        library.getBooks().forEach(System.out::println);
    }
}
