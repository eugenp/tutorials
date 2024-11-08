package com.baeldung.sequencenaming;

import org.hibernate.id.IdentifierGenerator;
import org.hibernate.engine.spi.SharedSessionContractImplementor;

import java.io.Serializable;
import java.util.UUID;

public class MovieIdGenerator implements IdentifierGenerator {
    private final String prefix = "MOVIE";

    public MovieIdGenerator() {
    }

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object obj) {
        if (obj instanceof Movie movie) {
            if (movie.getId() != null) {
                return movie.getId();
            }
        } else {
            return "MOVIE-" + UUID.randomUUID().toString();
        }
        return  null;
    }

    @Override
    public boolean allowAssignedIdentifiers() {
        return true;
    }
}
