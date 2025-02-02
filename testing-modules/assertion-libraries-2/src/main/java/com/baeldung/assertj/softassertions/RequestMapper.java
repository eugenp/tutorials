package com.baeldung.assertj.softassertions;

import java.util.UUID;

public class RequestMapper {

    public DomainModel map(Request request) {
        return new DomainModel(
            UUID.randomUUID().toString(),
            request.getType().equals("COMMON") ? 0 : 1,
            "NEW"
        );
    }
}
