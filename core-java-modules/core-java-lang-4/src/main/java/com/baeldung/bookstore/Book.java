// The next line is commented out to avoid the code to fail the build.
// package com.baeldung;

/**
* If the below package declaration is commented out and the above incorrect package 
* declaration is uncommented, then the problem will replicate.
*/
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
