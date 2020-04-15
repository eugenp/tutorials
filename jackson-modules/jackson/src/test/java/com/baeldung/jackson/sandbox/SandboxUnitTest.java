package com.baeldung.jackson.sandbox;

import java.io.IOException;

import org.junit.Test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SandboxUnitTest {

    @Test
    public final void whenDeserializing_thenCorrect() throws JsonParseException, JsonMappingException, IOException {
        final TestElement testElement = new TestElement();
        testElement.setX(10);
        testElement.setY("adasd");
        final ObjectMapper om = new ObjectMapper();
        om.setVisibility(om.getSerializationConfig()
            .getDefaultVisibilityChecker()
            .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
            .withGetterVisibility(JsonAutoDetect.Visibility.NONE));

        final String serialized = om.writeValueAsString(testElement);
        System.err.println(serialized);
    }

}
