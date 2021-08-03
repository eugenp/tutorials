//incorrect package com.baeldung declaration;
package com.baeldung.bookstore;

public class Book {

    private String title;
    private String author;
    private long isbn;
    
    public Book(String title, String author, long isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
    }
}
