package com.baeldung.dependency.ordering;

import org.apache.commons.collections4.MapUtils;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DependencyOrderingUnitTest {

    @Test
    void whenCorrectDependencyVersionIsUsed_thenShouldCompile() {
        assertEquals(0, MapUtils.size(new HashMap<>()));
    }
}
