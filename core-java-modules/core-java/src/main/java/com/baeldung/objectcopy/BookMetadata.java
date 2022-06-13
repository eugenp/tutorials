package com.baeldung.objectcopy;

public class BookMetadata {
    private int noOfPages;
    private String publisher;
    private int publishYear;
    private int price;

    public BookMetadata(int noOfPages, String publisher, int publishYear, int price) {
        this.noOfPages = noOfPages;
        this.publisher = publisher;
        this.publishYear = publishYear;
        this.price = price;
    }

    public BookMetadata(BookMetadata bookMetadata) {
        this.noOfPages = bookMetadata.getNoOfPages();
        this.publisher = bookMetadata.getPublisher();
        this.publishYear = bookMetadata.getPublishYear();
        this.price = bookMetadata.getPrice();
    }

    public int getNoOfPages() {
        return noOfPages;
    }

    public void setNoOfPages(int noOfPages) {
        this.noOfPages = noOfPages;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getPublishYear() {
        return publishYear;
    }

    public void setPublishYear(int publishYear) {
        this.publishYear = publishYear;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
