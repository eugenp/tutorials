package com.baeldung.batchtesting.model;

public class BookDetails {

    private String bookName;

    private String bookFormat;

    private String publishingYear;

    private String bookISBN;

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookFormat() {
        return bookFormat;
    }

    public void setBookFormat(String bookFormat) {
        this.bookFormat = bookFormat;
    }

    public String getPublishingYear() {
        return publishingYear;
    }

    public void setPublishingYear(String publishingYear) {
        this.publishingYear = publishingYear;
    }

    public String getBookISBN() {
        return bookISBN;
    }

    public void setBookISBN(String bookISBN) {
        this.bookISBN = bookISBN;
    }

    @Override
    public String toString() {
        return "BookDetails [bookName=" + bookName + ", bookFormat=" + bookFormat + ", publishingYear=" + publishingYear + ", bookISBN=" + bookISBN + "]";
    }

}
