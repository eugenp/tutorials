package com.baeldung.util;

import org.junit.Test;

import java.io.IOException;
import java.util.Properties;

import static org.junit.Assert.assertEquals;

public class PropertiesLoaderUnitTest {

    @Test
    public void loadProperties_whenPropertyReaded_thenSuccess() throws IOException {
        //given
        final String RESOURCE_FILE_NAME = "configuration.properties";

        final String SAMPLE_CONF_ENTRY = "sampleConfEntry";
        final String COLON_SEPARATED_CONF_ENTRY = "colonSeparatedEntry";

        final String GIVEN_CONF_ENTRY_VALUE = "sample String value";
        final String COLON_SEPARATED_CONF_ENTRY_VALUE = "colon separated entry value";

        //when
        Properties config = PropertiesLoader.loadProperties(RESOURCE_FILE_NAME);

        String sampleConfEntryValue = config.getProperty(SAMPLE_CONF_ENTRY);
        String colonSeparatedConfEntryValue = config.getProperty(COLON_SEPARATED_CONF_ENTRY);

        //then
        assertEquals(GIVEN_CONF_ENTRY_VALUE, sampleConfEntryValue);
        assertEquals(COLON_SEPARATED_CONF_ENTRY_VALUE, colonSeparatedConfEntryValue);

    }
}
