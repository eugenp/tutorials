package com.baeldung.property.json;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {JsonPropertyApplication.class})
public class JsonPropertySourceLoaderIntegrationTest {

    @Autowired
    private Environment env;

    @Test
    public void givenNonNullPropertyName_whenGetProperty_thenReturnNonNullPropertyValue() {
        String testNameValue = env.getProperty("outerName");
        assertNotNull(testNameValue);
        assertEquals("Outer Name", testNameValue);
    }

    @Test
    public void givenNonNullInnerPropertyName_whenGetProperty_thenReturnNonNullPropertyValue() {
        String testNameValue = env.getProperty("inner.name");
        assertNotNull(testNameValue);
        assertEquals("Inner Name", testNameValue);
    }
}
