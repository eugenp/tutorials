package com.baeldung.jacksonjr;

import java.time.LocalDate;

import com.fasterxml.jackson.jr.ob.api.ReaderWriterProvider;
import com.fasterxml.jackson.jr.ob.api.ValueReader;
import com.fasterxml.jackson.jr.ob.api.ValueWriter;
import com.fasterxml.jackson.jr.ob.impl.JSONReader;
import com.fasterxml.jackson.jr.ob.impl.JSONWriter;

public class MyHandlerProvider extends ReaderWriterProvider {

    @Override
    public ValueWriter findValueWriter(JSONWriter writeContext, Class<?> type) {
        if (type == LocalDate.class) {
            return new CustomDateSerializer();
        }
        return null;
    }

    @Override
    public ValueReader findValueReader(JSONReader readContext, Class<?> type) {
        if (type.equals(LocalDate.class)) {
            return new CustomDateDeserializer();
        }
        return null;
    }
}
