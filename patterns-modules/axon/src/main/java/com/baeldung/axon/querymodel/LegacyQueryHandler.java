package com.baeldung.axon.querymodel;

import com.baeldung.axon.coreapi.queries.TotalProductsShippedQuery;

import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;

@Service
public class LegacyQueryHandler {

    @QueryHandler
    public Integer handle(TotalProductsShippedQuery query) {
        switch (query.getProductId()) {
        case "Deluxe Chair":
            return 234;
        case "a6aa01eb-4e38-4dfb-b53b-b5b82961fbf3":
            return 10;
        default:
            return 0;
        }
    }
}
