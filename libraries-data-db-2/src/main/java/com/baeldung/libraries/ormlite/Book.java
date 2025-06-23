package com.baeldung.libraries.ormlite;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Book {

    @DatabaseField(generatedId = true)
    private long bookId;

    @DatabaseField
    private String title;

    @DatabaseField(foreign = true, foreignAutoRefresh = true, foreignAutoCreate = true)
    private Library library;

    public Book() {
    }

    public Book(String title) {
        this.title = title;
    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Library getLibrary() {
        return library;
    }

    public void setLibrary(Library library) {
        this.library = library;
    }

}
