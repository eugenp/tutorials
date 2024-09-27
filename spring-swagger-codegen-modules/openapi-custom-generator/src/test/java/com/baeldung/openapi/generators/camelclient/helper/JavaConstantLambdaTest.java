package com.baeldung.openapi.generators.camelclient.helper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.StringWriter;

import org.junit.jupiter.api.Test;

import com.samskivert.mustache.Template;

class JavaConstantLambdaUnitTest {

    @Test
    void whenExecute_thenSuccess() throws IOException {

        JavaConstantLambda sub = new JavaConstantLambda();

        StringWriter writer = new StringWriter();
        Template.Fragment frag = mock(Template.Fragment.class);
        when(frag.execute())
          .thenReturn("ANormalString");
        sub.execute(frag, writer);

        String result = writer.toString();
        assertEquals("A_NORMAL_STRING", result);

    }
}