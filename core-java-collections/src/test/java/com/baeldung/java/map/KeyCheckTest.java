package com.baeldung.java.map;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.MultiMap;
import org.apache.commons.collections4.MultiMapUtils;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.map.MultiValueMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.apache.commons.collections4.multimap.HashSetValuedHashMap;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.TreeMultimap;


public class KeyCheckTest {

    @Test
    public void whenKeyIsPresent_thenContainsKeyReturnsTrue() {
        Map<String, String> map = Collections.singletonMap("key", "value");

        assertTrue(map.containsKey("key"));
        assertFalse(map.containsKey("missing"));
    }

    @Test
    public void whenKeyHasNullValue_thenGetStillWorks() {
        Map<String, String> map = Collections.singletonMap("nothing", null);

        assertTrue(map.containsKey("nothing"));
        assertNull(map.get("nothing"));
    }
}