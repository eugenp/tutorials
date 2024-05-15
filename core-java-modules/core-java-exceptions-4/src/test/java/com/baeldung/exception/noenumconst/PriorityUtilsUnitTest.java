package com.baeldung.exception.noenumconst;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class PriorityUtilsUnitTest {

    @Test
    void givenCustomName_whenUsingGetByName_thenThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> PriorityUtils.getByName("Low"));
    }

    @Test
    void givenCustomName_whenUsingGetByUpperCaseName_thenReturnEnumConstant() {
        assertEquals(Priority.HIGH, PriorityUtils.getByUpperCaseName("High"));
    }

    @Test
    void givenInvalidCustomName_whenUsingGetByUpperCaseName_thenThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> PriorityUtils.getByUpperCaseName("invalid"));
    }

    @Test
    void givenEmptyName_whenUsingGetByUpperCaseName_thenReturnNull() {
        assertNull(PriorityUtils.getByUpperCaseName(""));
    }

    @Test
    void givenNull_whenUsingGetByUpperCaseName_thenReturnNull() {
        assertNull(PriorityUtils.getByUpperCaseName(null));
    }

}
