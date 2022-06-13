package com.baeldung.objectcopy;

public class ShallowCopyBook implements Cloneable {
    private String title;
    private String author;
    private String content;
    private BookMetadata bookMetadata;

    public ShallowCopyBook(String title, String author, String content, BookMetadata bookMetadata) {
        this.title = title;
        this.author = author;
        this.content = content;
        this.bookMetadata = bookMetadata;
    }

    public ShallowCopyBook(ShallowCopyBook copy) {
        this.title = copy.getTitle();
        this.author = copy.getAuthor();
        this.content = copy.getContent();
        this.bookMetadata = copy.getBookMetadata();
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public BookMetadata getBookMetadata() {
        return bookMetadata;
    }

    @Override
    public ShallowCopyBook clone() {
        try {
            return (ShallowCopyBook) super.clone();
        } catch (CloneNotSupportedException e) {
            return new ShallowCopyBook(this);
        }
    }
}
