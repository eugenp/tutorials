package com.baeldung.map.propertieshashmap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class PropertiesToHashMapConverterUnitTest {

    private Properties properties;

    private final static String propertyFileName = "toHashMap.properties";

    @BeforeEach
    public void setup() throws IOException {
        properties = new Properties();
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(propertyFileName)) {
            if (is != null) {
                properties.load(is);
            }
        }
    }

    @Test
    public void havingPropertiesLoaded_whenCheck_thenEquals() {
        assertEquals(3, properties.size());
        assertEquals("str_value", properties.get("property1"));
        assertEquals("123", properties.get("property2"));
        assertEquals("", properties.get("property3"));
    }

    @Test
    public void whenPropertiesModified_thenTypeSafeIssues() {
        compromiseProperties(properties);

        assertEquals(5, properties.size());

        assertNull(properties.getProperty("property4"));
        assertNotEquals(String.class, properties.get("property4").getClass());
        assertEquals(456, properties.get("property4"));


        assertNull(properties.getProperty("5"));
        assertNotEquals(String.class, properties.get(5).getClass());
        assertEquals(10.11, properties.get(5));
    }

    @Test
    public void havingNonModifiedProperties_whenTypeCastConvert_thenNoTypeSafeIssues() {
        HashMap<String, String> hMap = PropertiesToHashMapConverter.typeCastConvert(properties);

        assertEquals(3, hMap.size());
        assertEquals(String.class, hMap.get("property1").getClass());
        assertEquals(properties.get("property1"), hMap.get("property1"));
        assertEquals(String.class, hMap.get("property2").getClass());
        assertEquals(properties.get("property2"), hMap.get("property2"));
        assertEquals(String.class, hMap.get("property3").getClass());
        assertEquals(properties.get("property3"), hMap.get("property3"));
    }

    @Test
    public void havingModifiedProperties_whenTypeCastConvert_thenClassCastException() {
        compromiseProperties(properties);
        HashMap<String, String> hMap = PropertiesToHashMapConverter.typeCastConvert(properties);
        assertEquals(5, hMap.size());

        assertThrows(ClassCastException.class, () -> {
            String s = hMap.get("property4");
        });
        assertEquals(Integer.class, ((Object) hMap.get("property4")).getClass());

        assertNull(hMap.get("5"));
        assertNotNull(hMap.get(5));
        assertThrows(ClassCastException.class, () -> {
            String s = hMap.get(5);
        });
        assertEquals(Double.class, ((Object) hMap.get(5)).getClass());
    }

    @Test
    public void havingNonModifiedProperties_whenLoopConvert_thenNoTypeSafeIssues() {
        HashMap<String, String> hMap = PropertiesToHashMapConverter.loopConvert(properties);

        assertEquals(3, hMap.size());
        assertEquals(String.class, hMap.get("property1").getClass());
        assertEquals(properties.get("property1"), hMap.get("property1"));
        assertEquals(String.class, hMap.get("property2").getClass());
        assertEquals(properties.get("property2"), hMap.get("property2"));
        assertEquals(String.class, hMap.get("property3").getClass());
        assertEquals(properties.get("property3"), hMap.get("property3"));
    }

    @Test
    public void havingModifiedProperties_whenLoopConvert_thenNoClassCastException() {
        compromiseProperties(properties);
        HashMap<String, String> hMap = PropertiesToHashMapConverter.loopConvert(properties);
        assertEquals(5, hMap.size());

        assertDoesNotThrow(() -> {
            String s = hMap.get("property4");
        });
        assertEquals(String.class, hMap.get("property4").getClass());
        assertEquals("456", hMap.get("property4"));

        assertDoesNotThrow(() -> {
            String s = hMap.get("5");
        });
        assertEquals("10.11", hMap.get("5"));
    }

    @Test
    public void havingNonModifiedProperties_whenStreamConvert_thenNoTypeSafeIssues() {
        HashMap<String, String> hMap = PropertiesToHashMapConverter.streamConvert(properties);

        assertEquals(3, hMap.size());
        assertEquals(String.class, hMap.get("property1").getClass());
        assertEquals(properties.get("property1"), hMap.get("property1"));
        assertEquals(String.class, hMap.get("property2").getClass());
        assertEquals(properties.get("property2"), hMap.get("property2"));
        assertEquals(String.class, hMap.get("property3").getClass());
        assertEquals(properties.get("property3"), hMap.get("property3"));
    }

    @Test
    public void havingModifiedProperties_whenStreamConvert_thenNoClassCastException() {
        compromiseProperties(properties);
        HashMap<String, String> hMap = PropertiesToHashMapConverter.streamConvert(properties);
        assertEquals(5, hMap.size());

        assertDoesNotThrow(() -> {
            String s = hMap.get("property4");
        });
        assertEquals(String.class, hMap.get("property4").getClass());
        assertEquals("456", hMap.get("property4"));

        assertDoesNotThrow(() -> {
            String s = hMap.get("5");
        });
        assertEquals("10.11", hMap.get("5"));
    }

    @Test
    public void havingModifiedProperties_whenLoopConvertAndStreamConvert_thenHashMapsSame() {
        compromiseProperties(properties);
        HashMap<String, String> hMap1 = PropertiesToHashMapConverter.loopConvert(properties);
        HashMap<String, String> hMap2 = PropertiesToHashMapConverter.streamConvert(properties);

        assertEquals(hMap2, hMap1);
    }

    @Test
    public void havingNonModifiedProperties_whenGuavaConvert_thenNoTypeSafeIssues() {
        HashMap<String, String> hMap = PropertiesToHashMapConverter.guavaConvert(properties);

        assertEquals(3, hMap.size());
        assertEquals(String.class, hMap.get("property1").getClass());
        assertEquals(properties.get("property1"), hMap.get("property1"));
        assertEquals(String.class, hMap.get("property2").getClass());
        assertEquals(properties.get("property2"), hMap.get("property2"));
        assertEquals(String.class, hMap.get("property3").getClass());
        assertEquals(properties.get("property3"), hMap.get("property3"));
    }

    @Test
    public void havingModifiedProperties_whenGuavaConvert_thenUnableToConvertAndThrowException() {
        compromiseProperties(properties);
        assertThrows(Exception.class, () -> PropertiesToHashMapConverter.guavaConvert(properties));
    }

    @Test
    public void havingModifiedPropertiesWithNoIntegerValue_whenGuavaConvert_thenNullPointerException() {
        properties.put("property4", 456);
        assertThrows(NullPointerException.class, () -> PropertiesToHashMapConverter.guavaConvert(properties));
    }

    @Test
    public void havingModifiedPropertiesWithNoIntegerKey_whenGuavaConvert_thenClassCastException() {
        properties.put(5, 10.11);
        assertThrows(ClassCastException.class, () -> PropertiesToHashMapConverter.guavaConvert(properties));
    }


    private void compromiseProperties(Properties prop) {
        prop.put("property4", 456);
        prop.put(5, 10.11);
    }
}