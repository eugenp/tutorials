package com.baeldung.smithy.books.server;

import com.baeldung.smithy.books.server.model.GetBookInput;
import com.baeldung.smithy.books.server.model.GetBookOutput;
import com.baeldung.smithy.books.server.service.GetBookOperation;
import software.amazon.smithy.java.server.RequestContext;

class GetBookOperationImpl implements GetBookOperation {
    public GetBookOutput getBook(GetBookInput input, RequestContext context) {
        return GetBookOutput.builder()
            .bookId(input.bookId())
            .title("Head First Java, 3rd Edition: A Brain-Friendly Guide")
            .author("Kathy Sierra, Bert Bates, Trisha Gee")
            .isbn("9781491910771")
            .publishedYear(2022)
            .build();
    }
}
