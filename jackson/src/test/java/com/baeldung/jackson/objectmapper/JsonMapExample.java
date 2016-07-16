package com.baeldung.jackson.objectmapper;

import static org.junit.Assert.assertNotNull;

import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonMapExample extends Example {

    protected final Logger Logger = LoggerFactory.getLogger(getClass());

    public JsonMapExample() { }

    @Override
    public String name()
    {
        return this.getClass().getName();
    }

    @Override
    public void execute()
    {
        final ObjectMapper objectMapper = new ObjectMapper();
        try
        {
            final Map<String, Object> map = objectMapper.readValue(EXAMPLE_JSON, new TypeReference<Map<String, Object>>() {
            });
            for(final String key : map.keySet())
            {
                Logger.debug("key = " + key + " | value = " + map.get(key));
            }
        }
        catch (final Exception e)
        {
            Logger.error(e.toString());
        }
    }

    @Override
    @Test
    public void test() throws Exception {
        final ObjectMapper objectMapper = new ObjectMapper();
        final Map<String, Object> map = objectMapper.readValue(EXAMPLE_JSON, new TypeReference<Map<String, Object>>() {
        });
        assertNotNull(map);
        for (final String key : map.keySet()) {
            assertNotNull(key);
        }
    }
}
