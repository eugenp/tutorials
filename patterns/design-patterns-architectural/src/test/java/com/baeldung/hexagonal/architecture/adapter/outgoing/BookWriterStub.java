package com.baeldung.hexagonal.architecture.adapter.outgoing;

import com.baeldung.hexagonal.architecture.domain.Book;
import com.baeldung.hexagonal.architecture.port.outgoing.BookWriter;

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
