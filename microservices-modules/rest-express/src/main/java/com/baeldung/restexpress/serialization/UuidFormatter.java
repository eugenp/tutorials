package com.baeldung.restexpress.serialization;

import com.strategicgains.hyperexpress.annotation.TokenFormatter;
import com.strategicgains.repoexpress.util.UuidConverter;

import java.util.UUID;

public class UuidFormatter
        implements TokenFormatter {
    @Override
    public String format(Object field) {
        return UuidConverter.format((UUID) field);
    }
}
