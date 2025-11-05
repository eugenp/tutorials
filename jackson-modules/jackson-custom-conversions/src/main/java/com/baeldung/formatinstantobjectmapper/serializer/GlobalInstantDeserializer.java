package com.baeldung.formatinstantobjectmapper.serializer;

import java.time.Instant;

import com.baeldung.formatinstantobjectmapper.utils.Instants;
import com.fasterxml.jackson.datatype.jsr310.deser.InstantDeserializer;

public class GlobalInstantDeserializer extends InstantDeserializer<Instant> {
    private static final long serialVersionUID = 1L;

    public GlobalInstantDeserializer() {
        super(InstantDeserializer.INSTANT, Instants.FORMATTER);
    }
}
