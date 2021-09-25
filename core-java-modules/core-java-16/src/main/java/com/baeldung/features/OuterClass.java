package com.baeldung.features;

import com.baeldung.features.record.Book;

public class OuterClass {
    class InnerClass {
        Book book = new Book("Title", "author", "isbn");
    }
}
