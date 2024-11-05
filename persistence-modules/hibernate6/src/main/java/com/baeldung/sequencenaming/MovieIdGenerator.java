package com.baeldung.sequencenaming;

import org.hibernate.id.IdentifierGenerator;
import org.hibernate.engine.spi.SharedSessionContractImplementor;

import java.io.Serializable;

public class MovieIdGenerator implements IdentifierGenerator {
    private final String prefix = "MOVIE";

    public MovieIdGenerator() {
    }

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object obj) {
        Long count = (Long) session.createQuery("SELECT COUNT(m.id) FROM Movie m").uniqueResult();
        long newId = count != null ? count + 1 : 1;
        return prefix + "-" + newId;
    }
}
