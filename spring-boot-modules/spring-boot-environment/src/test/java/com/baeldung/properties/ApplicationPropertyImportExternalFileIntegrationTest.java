package com.baeldung.properties;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ApplicationPropertyImportExternalFileIntegrationTest {

    @Value("${bael.property1}")
    String baelProperty;

    @Test
    public void whenExternalisedPropertiesLoadedUsinApplicationProperties_thenReadValues() throws IOException {
        assertEquals(baelProperty, "value1");
    }

}
