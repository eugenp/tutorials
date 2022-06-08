package com.baeuldung.jsdc.copy;

import com.baeuldung.jsdc.model.Book;

public class DeepCopier {

    public Book deepCopy(Book book) {
        Book deepCopy = new Book(book.getTitle(), book.getAuthor());
        return deepCopy;
    }
}
