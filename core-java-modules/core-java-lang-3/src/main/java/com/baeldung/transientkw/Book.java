package com.baeldung.transientkw;

import java.io.Serializable;

public class Book implements Serializable {

    private static final long serialVersionUID = -2936687026040726549L;

    private String bookName;
    private transient String description;
    private transient int copies;
    private final transient String bookCategory = "Fiction";

    private final transient String bookCategoryNewOperator = new String("Fiction with new Operator");

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCopies() {
        return copies;
    }

    public void setCopies(int copies) {
        this.copies = copies;
    }

    public String getBookCategory() {
        return bookCategory;
    }

    public String getBookCategoryNewOperator() {
        return bookCategoryNewOperator;
    }
}
