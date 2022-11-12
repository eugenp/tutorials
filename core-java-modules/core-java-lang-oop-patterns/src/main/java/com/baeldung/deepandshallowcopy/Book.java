package com.baeldung.deepandshallowcopy;

public class Book implements Cloneable{
    private String title;
    private String author;

    private Isbn isbn;

    public Book() {
    }

    @Override
    public Object clone()
    {
        Book clonedBook = null;
        try {
            clonedBook = (Book) super.clone();
        }catch(CloneNotSupportedException e){
            clonedBook = new Book(this.getTitle(), this.getAuthor(), (Isbn) this.isbn.clone());
        }
        return clonedBook;
    }

    public Book(String title, String author, Isbn isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
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

    public Isbn getIsbn() {
        return isbn;
    }

    public void setIsbn(Isbn isbn) {
        this.isbn = isbn;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}