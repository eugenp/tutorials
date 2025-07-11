package com.baeldung.smithy.books.server;

import com.baeldung.smithy.books.server.model.CreateBookInput;
import com.baeldung.smithy.books.server.model.CreateBookOutput;
import com.baeldung.smithy.books.server.service.CreateBookOperation;
import software.amazon.smithy.java.server.RequestContext;

public class CreateBookOperationImpl implements CreateBookOperation {
    @Override
    public CreateBookOutput createBook(CreateBookInput input, RequestContext context) {
        throw new UnsupportedOperationException();
    }
}
