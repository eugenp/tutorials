package com.baeldung.java_8_features.groupingby;

public class Tuple {

    private BlogPostType type;
    private String author;
    
    public Tuple(BlogPostType type, String author) {
        super();
        this.type = type;
        this.author = author;
    }

    public BlogPostType getType() {
        return type;
    }

    public void setType(BlogPostType type) {
        this.type = type;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Tuple [type=" + type + ", author=" + author + ", getType()=" + getType() + ", getAuthor()=" + getAuthor() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
    }
}
