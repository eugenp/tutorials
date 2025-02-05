package com.baeldung.cats.config;

import java.io.IOException;

import com.baeldung.cats.utils.RegexUtils;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class ZeroWidthIntDeserializer extends JsonDeserializer<Integer> {

    @Override
    public Integer deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        return Integer.valueOf(RegexUtils.removeZeroWidthChars(parser.getText()));
    }
}
