package com.baeldung.hexagonal.adapter.outgoing;

import com.baeldung.hexagonal.domain.Book;
import com.baeldung.hexagonal.port.outgoing.BookWriter;

import java.util.ArrayList;
import java.util.List;

public class BookWriterStub implements BookWriter {
    List<String> output = new ArrayList<>();

    @Override
    public void writeBooks(List<Book> books) {
        books.forEach(book -> output.add(book.getTitle() + " - " + book.getAuthor()));
    }

    public List<String> getWrittenOutLines() {
        return output;
    }
}
