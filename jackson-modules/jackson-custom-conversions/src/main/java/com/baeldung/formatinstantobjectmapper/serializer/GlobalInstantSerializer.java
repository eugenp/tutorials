package com.baeldung.formatinstantobjectmapper.serializer;

import com.baeldung.formatinstantobjectmapper.utils.Instants;
import com.fasterxml.jackson.datatype.jsr310.ser.InstantSerializer;

public class GlobalInstantSerializer extends InstantSerializer {
    private static final long serialVersionUID = 1L;

    public GlobalInstantSerializer() {
        super(InstantSerializer.INSTANCE, false, false, Instants.FORMATTER);
    }
}
