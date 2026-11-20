package com.baeldung.exceptions.classcastexception;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class NumericWrapperCastsUnitTest {

    @Test
    void givenObjectValueReferencingInteger_whenCastToLong_thenClassCastException() {
        Map<String, Object> row = new HashMap<>();
        row.put("count", 42);
        assertThrows(ClassCastException.class, () -> {
            Long count = (Long) row.get("count");
        });
    }

    @Test
    void givenLongReference_whenConvertedWithIntValue_thenNoClassCastException() {
        Long id = 42L;
        Integer count = id.intValue();

        assertEquals(id, count.longValue());
    }

    @Test
    void givenObjectValueReferencingInteger_whenConvertedWithLongValue_thenNoClassCastException() {
        Integer id = 42;
        Map<String, Object> row = new HashMap<>();
        row.put("count", id);
        Long count = ((Integer) row.get("count")).longValue();

        assertEquals(id, count.intValue());
    }
}