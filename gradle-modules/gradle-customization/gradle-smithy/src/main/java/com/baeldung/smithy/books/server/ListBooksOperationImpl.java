package com.baeldung.smithy.books.server;

import com.baeldung.smithy.books.server.model.ListBooksInput;
import com.baeldung.smithy.books.server.model.ListBooksOutput;
import com.baeldung.smithy.books.server.service.ListBooksOperation;
import software.amazon.smithy.java.server.RequestContext;

public class ListBooksOperationImpl implements ListBooksOperation {
    @Override
    public ListBooksOutput listBooks(ListBooksInput input, RequestContext context) {
        throw new UnsupportedOperationException();
    }
}
