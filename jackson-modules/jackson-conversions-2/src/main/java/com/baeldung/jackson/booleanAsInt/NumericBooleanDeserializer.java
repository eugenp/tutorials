package com.baeldung.jackson.booleanAsInt;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;

public class NumericBooleanDeserializer extends JsonDeserializer<Boolean> {

    @Override
    public Boolean deserialize(JsonParser p, DeserializationContext ctxt)
      throws IOException {
        if ("1".equals(p.getText())) {
            return Boolean.TRUE;
        }
        if ("0".equals(p.getText())) {
            return Boolean.FALSE;
        }
        // for other than "1" or "0" throw exception by using Jackson internals
        throw ctxt.weirdStringException(p.getText(), Boolean.class, "only \"1\" or \"0\" recognized");
    }

}
