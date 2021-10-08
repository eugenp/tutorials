package com.baeldung.map.keysetValuesEntrySet;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class ValuesExampleUnitTest {

    private final Map<String, String> hashMap = new HashMap<String, String>() {{
        put("key1", "value1");
        put("key2", "value2");
    }};

    @Test
    public void givenHashMap_whenPassed_thenPrintValue() {
        ValuesExample.printValues(hashMap);
    }

}
