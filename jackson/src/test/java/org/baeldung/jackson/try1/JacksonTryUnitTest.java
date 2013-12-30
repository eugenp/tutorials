package org.baeldung.jackson.try1;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;

public class JacksonTryUnitTest {

    @Test
    public final void whenDeserializing_thenCorrect() throws JsonParseException, JsonMappingException, IOException {
        final URL url = Resources.getResource("example2.json");
        final String jsonAsString = Resources.toString(url, Charsets.UTF_8);

        final Collection<COrder> readValues = new ObjectMapper().readValue(jsonAsString, new TypeReference<Collection<COrder>>() {
        });

        assertNotNull(readValues);
    }

}
