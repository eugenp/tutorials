/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.catalina.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestParameterMap {

    private static final String[] TEST_PARAM_VALUES_1 = { "value1" };
    private static final String[] TEST_PARAM_VALUES_2 = { "value2" };
    private static final String[] TEST_PARAM_VALUES_2_UPDATED = { "value2-updated" };
    private static final String[] TEST_PARAM_VALUES_3 = { "value3" };

    private Map<String, String[]> paramMap;

    @Before
    public void setUp() {
        paramMap = new ParameterMap<String, String[]>();

        paramMap.put("param1", TEST_PARAM_VALUES_1);
        paramMap.put("param2", TEST_PARAM_VALUES_2);
        paramMap.put("param3", TEST_PARAM_VALUES_3);

        Assert.assertTrue(paramMap.containsKey("param1"));
        Assert.assertArrayEquals(TEST_PARAM_VALUES_1, paramMap.get("param1"));
        Assert.assertTrue(paramMap.containsKey("param2"));
        Assert.assertArrayEquals(TEST_PARAM_VALUES_2, paramMap.get("param2"));
        Assert.assertTrue(paramMap.containsKey("param3"));
        Assert.assertArrayEquals(TEST_PARAM_VALUES_3, paramMap.get("param3"));

        final Set<String> keySet = paramMap.keySet();
        Assert.assertTrue(keySet.contains("param1"));
        Assert.assertTrue(keySet.contains("param2"));
        Assert.assertTrue(keySet.contains("param3"));

        paramMap.put("param2", TEST_PARAM_VALUES_2_UPDATED);
        paramMap.remove("param3");

        Assert.assertTrue(paramMap.containsKey("param1"));
        Assert.assertArrayEquals(TEST_PARAM_VALUES_1, paramMap.get("param1"));
        Assert.assertTrue(paramMap.containsKey("param2"));
        Assert.assertArrayEquals(TEST_PARAM_VALUES_2_UPDATED, paramMap.get("param2"));
        Assert.assertFalse(paramMap.containsKey("param3"));
        Assert.assertNull(paramMap.get("param3"));

        Assert.assertTrue(keySet.contains("param1"));
        Assert.assertTrue(keySet.contains("param2"));
        Assert.assertFalse(keySet.contains("param3"));
    }

    @After
    public void tearDown() {
        Assert.assertTrue(paramMap.containsKey("param1"));
        Assert.assertArrayEquals(TEST_PARAM_VALUES_1, paramMap.get("param1"));
        Assert.assertTrue(paramMap.containsKey("param2"));
        Assert.assertArrayEquals(TEST_PARAM_VALUES_2_UPDATED, paramMap.get("param2"));
        Assert.assertFalse(paramMap.containsKey("param3"));
        Assert.assertNull(paramMap.get("param3"));
    }

    @Test
    public void testMapImmutabilityAfterLocked() {
        ((ParameterMap<String, String[]>) paramMap).setLocked(true);

        try {
            String[] updatedParamValues22 = new String[] { "value2-updated-2" };
            paramMap.put("param2", updatedParamValues22);
            Assert.fail("ParameterMap is not locked.");
        } catch (IllegalStateException expectedException) {
        }

        try {
            final Map<String, String[]> additionalParams = new HashMap<String, String[]>();
            additionalParams.put("param4", new String[] { "value4" });
            paramMap.putAll(additionalParams);
            Assert.fail("ParameterMap is not locked.");
        } catch (IllegalStateException expectedException) {
        }

        try {
            paramMap.remove("param2");
            Assert.fail("ParameterMap is not locked.");
        } catch (IllegalStateException expectedException) {
        }

        try {
            paramMap.clear();
            Assert.fail("ParameterMap is not locked.");
        } catch (IllegalStateException expectedException) {
        }
    }

    @Test
    public void testKeySetImmutabilityAfterLocked() {
        ((ParameterMap<String, String[]>) paramMap).setLocked(true);

        final Set<String> keySet = paramMap.keySet();

        try {
            keySet.add("param4");
            Assert.fail("ParameterMap is not locked.");
        } catch (UnsupportedOperationException expectedException) {
        }

        try {
            keySet.remove("param2");
            Assert.fail("ParameterMap is not locked.");
        } catch (UnsupportedOperationException expectedException) {
        }

        try {
            keySet.removeAll(Arrays.asList("param1", "param2"));
            Assert.fail("ParameterMap is not locked.");
        } catch (UnsupportedOperationException expectedException) {
        }

        try {
            keySet.retainAll(Collections.emptyList());
            Assert.fail("ParameterMap is not locked.");
        } catch (UnsupportedOperationException expectedException) {
        }

        try {
            keySet.clear();
            Assert.fail("ParameterMap is not locked.");
        } catch (UnsupportedOperationException expectedException) {
        }
    }

    @Test
    public void testValuesImmutabilityAfterLocked() {
        ((ParameterMap<String, String[]>) paramMap).setLocked(true);

        final Collection<String[]> valuesCol = paramMap.values();

        try {
            valuesCol.add(new String[] { "value4" });
            Assert.fail("ParameterMap is not locked.");
        } catch (UnsupportedOperationException expectedException) {
        }

        try {
            List<String[]> list = new ArrayList<String[]>();
            list.add(new String[] { "value4" });
            valuesCol.addAll(list);
            Assert.fail("ParameterMap is not locked.");
        } catch (UnsupportedOperationException expectedException) {
        }

        try {
            valuesCol.remove(TEST_PARAM_VALUES_1);
            Assert.fail("ParameterMap is not locked.");
        } catch (UnsupportedOperationException expectedException) {
        }

        try {
            List<String[]> list = new ArrayList<String[]>();
            list.add(TEST_PARAM_VALUES_1);
            valuesCol.removeAll(list);
            Assert.fail("ParameterMap is not locked.");
        } catch (UnsupportedOperationException expectedException) {
        }

        try {
            valuesCol.retainAll(Collections.emptyList());
            Assert.fail("ParameterMap is not locked.");
        } catch (UnsupportedOperationException expectedException) {
        }

        try {
            valuesCol.clear();
            Assert.fail("ParameterMap is not locked.");
        } catch (UnsupportedOperationException expectedException) {
        }
    }

    @Test
    public void testEntrySetImmutabilityAfterLocked() {
        ((ParameterMap<String, String[]>) paramMap).setLocked(true);

        final Set<Map.Entry<String, String[]>> entrySet = paramMap.entrySet();

        try {
            final Map<String, String[]> anotherParamsMap = new HashMap<String, String[]>();
            anotherParamsMap.put("param4", new String[] { "value4" });
            Map.Entry<String, String[]> anotherEntry = anotherParamsMap.entrySet().iterator().next();
            entrySet.add(anotherEntry);
            Assert.fail("ParameterMap is not locked.");
        } catch (UnsupportedOperationException expectedException) {
        }

        try {
            final Map<String, String[]> anotherParamsMap = new HashMap<String, String[]>();
            anotherParamsMap.put("param4", new String[] { "value4" });
            anotherParamsMap.put("param5", new String[] { "value5" });
            entrySet.addAll(anotherParamsMap.entrySet());
            Assert.fail("ParameterMap is not locked.");
        } catch (UnsupportedOperationException expectedException) {
        }

        try {
            final Map.Entry<String, String[]> entry = entrySet.iterator().next();
            entrySet.remove(entry);
            Assert.fail("ParameterMap is not locked.");
        } catch (UnsupportedOperationException expectedException) {
        }

        try {
            Set<Map.Entry<String, String[]>> anotherEntrySet = new HashSet<Entry<String, String[]>>(entrySet);
            entrySet.removeAll(anotherEntrySet);
            Assert.fail("ParameterMap is not locked.");
        } catch (UnsupportedOperationException expectedException) {
        }

        try {
            entrySet.retainAll(Collections.emptySet());
            Assert.fail("ParameterMap is not locked.");
        } catch (UnsupportedOperationException expectedException) {
        }

        try {
            entrySet.clear();
            Assert.fail("ParameterMap is not locked.");
        } catch (UnsupportedOperationException expectedException) {
        }
    }
}