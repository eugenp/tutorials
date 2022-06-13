package com.baeldung.objectcopy;

public class DeepCopyBook implements Cloneable {
    private String title;
    private String author;
    private String content;
    private BookMetadata bookMetadata;

    public DeepCopyBook(String title, String author, String content, BookMetadata bookMetadata) {
        this.title = title;
        this.author = author;
        this.content = content;
        this.bookMetadata = bookMetadata;
    }

    public DeepCopyBook(DeepCopyBook copy) {
        this.title = copy.getTitle();
        this.author = copy.getAuthor();
        this.content = copy.getContent();
        this.bookMetadata = new BookMetadata(copy.getBookMetadata());
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
    public DeepCopyBook clone() {
        return new DeepCopyBook(this);
    }
}
