package com.baeuldung.jsdc.copy;

import com.baeuldung.jsdc.model.Book;

public class ShallowCopier extends Copier {

    @Override
    public Book copy(Book book) {
        Book shallowCopy = book;
        return shallowCopy;
    }
}
