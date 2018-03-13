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
package javax.el;

import java.beans.FeatureDescriptor;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import org.apache.jasper.el.ELContextImpl;

public class TestMapELResolver {

    /**
     * Tests that a null context results in an NPE as per EL Javadoc.
     */
    @Test(expected = NullPointerException.class)
    public void testGetType01() {
        MapELResolver mapELResolver = new MapELResolver();
        mapELResolver.getType(null, new Object(), new Object());
    }

    /**
     * Tests that a valid property is not resolved if base is not Map.
     */
    @Test
    public void testGetType02() {
        doNegativeTest(new Object(), new Object(), MethodUnderTest.GET_TYPE,
                true);
    }

    /**
     * Tests that a valid property is resolved.
     */
    @Test
    public void testGetType03() {
        MapELResolver mapELResolver = new MapELResolver();
        ELContext context = new ELContextImpl();

        Class<?> result = mapELResolver.getType(context,
                new HashMap<Object, Object>(), "test");

        Assert.assertEquals(Object.class, result);
        Assert.assertTrue(context.isPropertyResolved());
    }

    /**
     * Tests that a null context results in an NPE as per EL Javadoc.
     */
    @Test(expected = NullPointerException.class)
    public void testGetValue01() {
        MapELResolver mapELResolver = new MapELResolver();
        mapELResolver.getValue(null, new Object(), new Object());
    }

    /**
     * Tests that a valid property is not resolved if base is not Map.
     */
    @Test
    public void testGetValue02() {
        doNegativeTest(new Object(), new Object(), MethodUnderTest.GET_VALUE,
                true);
    }

    /**
     * Tests that a valid property is resolved.
     */
    @Test
    public void testGetValue03() {
        MapELResolver mapELResolver = new MapELResolver();
        ELContext context = new ELContextImpl();

        Map<String, String> map = new HashMap<String, String>();
        map.put("key", "value");
        Object result = mapELResolver.getValue(context, map, "key");

        Assert.assertEquals("value", result);
        Assert.assertTrue(context.isPropertyResolved());

        result = mapELResolver.getValue(context, map, "unknown-key");

        Assert.assertNull(result);
        Assert.assertTrue(context.isPropertyResolved());
    }

    /**
     * Tests that a null context results in an NPE as per EL Javadoc.
     */
    @Test(expected = NullPointerException.class)
    public void testSetValue01() {
        MapELResolver mapELResolver = new MapELResolver();
        mapELResolver.setValue(null, new Object(), new Object(), new Object());
    }

    /**
     * Tests that a valid property is not set if base is not Map.
     */
    @Test
    public void testSetValue02() {
        doNegativeTest(new Object(), new Object(), MethodUnderTest.SET_VALUE,
                false);
    }

    /**
     * Tests that an exception is thrown when readOnly is true.
     */
    @Test(expected = PropertyNotWritableException.class)
    public void testSetValue03() {
        MapELResolver mapELResolver = new MapELResolver(true);
        ELContext context = new ELContextImpl();

        mapELResolver.setValue(context, new HashMap<Object, Object>(),
                new Object(), new Object());
    }

    /**
     * Tests that a valid property is set.
     */
    @Test
    public void testSetValue04() {
        MapELResolver mapELResolver = new MapELResolver();
        ELContext context = new ELContextImpl();

        Map<String, String> map = new HashMap<String, String>();
        mapELResolver.setValue(context, map, "key", "value");

        Assert.assertEquals("value",
                mapELResolver.getValue(context, map, "key"));
        Assert.assertTrue(context.isPropertyResolved());
    }

    /**
     * Tests that an exception is thrown when the map is not modifiable.
     */
    @Test(expected = PropertyNotWritableException.class)
    public void testSetValue05() {
        MapELResolver mapELResolver = new MapELResolver();
        ELContext context = new ELContextImpl();

        Map<Object, Object> map = Collections
                .unmodifiableMap(new HashMap<Object, Object>());
        mapELResolver.setValue(context, map, "key", "value");
    }

    /**
     * Tests that a null context results in an NPE as per EL Javadoc.
     */
    @Test(expected = NullPointerException.class)
    public void testIsReadOnly01() {
        MapELResolver mapELResolver = new MapELResolver();
        mapELResolver.isReadOnly(null, new Object(), new Object());
    }

    /**
     * Tests that the propertyResolved is false if base is not Map.
     */
    @Test
    public void testIsReadOnly02() {
        MapELResolver mapELResolver = new MapELResolver();
        ELContext context = new ELContextImpl();

        boolean result = mapELResolver.isReadOnly(context, new Object(),
                new Object());

        Assert.assertFalse(result);
        Assert.assertFalse(context.isPropertyResolved());

        mapELResolver = new MapELResolver(true);

        result = mapELResolver.isReadOnly(context, new Object(), new Object());

        Assert.assertTrue(result);
        Assert.assertFalse(context.isPropertyResolved());
    }

    /**
     * Tests that if the MapELResolver is constructed with readOnly the method
     * will return always true, otherwise false.
     */
    @Test
    public void testIsReadOnly03() {
        MapELResolver mapELResolver = new MapELResolver();
        ELContext context = new ELContextImpl();

        boolean result = mapELResolver.isReadOnly(context,
                new HashMap<Object, Object>(), new Object());

        Assert.assertFalse(result);
        Assert.assertTrue(context.isPropertyResolved());

        mapELResolver = new MapELResolver(true);

        result = mapELResolver.isReadOnly(context,
                new HashMap<Object, Object>(), new Object());

        Assert.assertTrue(result);
        Assert.assertTrue(context.isPropertyResolved());
    }

    /**
     * Tests that the readOnly is true always when the map is not modifiable.
     */
    @Test
    public void testIsReadOnly04() {
        MapELResolver mapELResolver = new MapELResolver();
        ELContext context = new ELContextImpl();

        Map<Object, Object> map = Collections
                .unmodifiableMap(new HashMap<Object, Object>());
        boolean result = mapELResolver.isReadOnly(context, map, new Object());

        Assert.assertTrue(result);
        Assert.assertTrue(context.isPropertyResolved());
    }

    /**
     * Tests that a valid FeatureDescriptors are not returned if base is not
     * Map.
     */
    @Test
    public void testGetFeatureDescriptors01() {
        MapELResolver mapELResolver = new MapELResolver();
        ELContext context = new ELContextImpl();

        Iterator<FeatureDescriptor> result = mapELResolver
                .getFeatureDescriptors(context, new Object());

        Assert.assertNull(result);
    }

    /**
     * Tests that a valid FeatureDescriptors are returned.
     */
    @Test
    public void testGetFeatureDescriptors02() {
        MapELResolver mapELResolver = new MapELResolver();
        ELContext context = new ELContextImpl();

        Map<String, String> map = new HashMap<String, String>();
        map.put("key", "value");
        Iterator<FeatureDescriptor> result = mapELResolver
                .getFeatureDescriptors(context, map);

        while (result.hasNext()) {
            FeatureDescriptor featureDescriptor = result.next();
            Assert.assertEquals("key", featureDescriptor.getDisplayName());
            Assert.assertEquals("key", featureDescriptor.getName());
            Assert.assertEquals("", featureDescriptor.getShortDescription());
            Assert.assertFalse(featureDescriptor.isExpert());
            Assert.assertFalse(featureDescriptor.isHidden());
            Assert.assertTrue(featureDescriptor.isPreferred());
            Assert.assertEquals("key".getClass(),
                    featureDescriptor.getValue(ELResolver.TYPE));
            Assert.assertEquals(Boolean.TRUE, featureDescriptor
                    .getValue(ELResolver.RESOLVABLE_AT_DESIGN_TIME));
        }
    }

    private void doNegativeTest(Object base, Object trigger,
            MethodUnderTest method, boolean checkResult) {
        MapELResolver resolver = new MapELResolver();
        ELContext context = new ELContextImpl();

        Object result = null;
        switch (method) {
        case GET_VALUE: {
            result = resolver.getValue(context, base, trigger);
            break;
        }
        case SET_VALUE: {
            resolver.setValue(context, base, trigger, new Object());
            break;
        }
        case GET_TYPE: {
            result = resolver.getType(context, base, trigger);
            break;
        }
        default: {
            // Should never happen
            Assert.fail("Missing case for method");
        }
        }

        if (checkResult) {
            Assert.assertNull(result);
        }
        Assert.assertFalse(context.isPropertyResolved());
    }

    private static enum MethodUnderTest {
        GET_VALUE, SET_VALUE, GET_TYPE
    }
}
