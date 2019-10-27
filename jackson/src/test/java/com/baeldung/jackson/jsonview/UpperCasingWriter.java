package com.baeldung.jackson.jsonview;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;

public class UpperCasingWriter extends BeanPropertyWriter {
    final BeanPropertyWriter _writer;

    public UpperCasingWriter(final BeanPropertyWriter w) {
        super(w);
        _writer = w;
    }

    @Override
    public void serializeAsField(final Object bean, final JsonGenerator gen, final SerializerProvider prov) throws Exception {
        String value = ((User) bean).name;
        value = (value == null) ? "" : value.toUpperCase();
        gen.writeStringField("name", value);
    }
}
