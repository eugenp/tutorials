package com.baeldung.smithy.books.server;

import com.baeldung.smithy.books.server.model.RecommendBookInput;
import com.baeldung.smithy.books.server.model.RecommendBookOutput;
import com.baeldung.smithy.books.server.service.RecommendBookOperation;
import software.amazon.smithy.java.server.RequestContext;

public class RecommendBookOperationImpl implements RecommendBookOperation {
    @Override
    public RecommendBookOutput recommendBook(RecommendBookInput input, RequestContext context) {
        throw new UnsupportedOperationException();
    }
}
