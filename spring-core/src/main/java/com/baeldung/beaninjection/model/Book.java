package com.baeldung.beaninjection.model;


/**
 * This is the Book entity class for Book Services.
 * @Author Akshay Desale.
 */
public class Book {

    private String title,author;
    
    public Book() {
      }

    public Book(String title, String author) {
        super();
        this.title = title;
        this.author = author;
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

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Book))
            return false;
        
        Book book = (Book)obj;
        return (this.getTitle().equalsIgnoreCase(book.getTitle()) && (this.getAuthor().equalsIgnoreCase(book.getAuthor())));
    }
    
    @Override
    public String toString() {
        return "Book [title=" + title + ", author=" + author + "]";
    }    
}
