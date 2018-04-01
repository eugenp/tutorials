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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import org.apache.jasper.el.ELContextImpl;

public class TestListELResolver {

    /**
     * Tests that a null context results in an NPE as per EL Javadoc.
     */
    @Test(expected = NullPointerException.class)
    public void testGetValue01() {
        ListELResolver resolver = new ListELResolver();
        resolver.getValue(null, new Object(), new Object());
    }

    /**
     * Tests that a valid property is not resolved if base is not List.
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
        ListELResolver resolver = new ListELResolver();
        ELContext context = new ELContextImpl();

        List<String> list = new ArrayList<String>();
        list.add("key");
        Object result = resolver.getValue(context, list, new Integer(0));

        Assert.assertEquals("key", result);
        Assert.assertTrue(context.isPropertyResolved());
    }

    /**
     * Tests a coercion cannot be performed as the key is not integer.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetValue04() {
        ListELResolver resolver = new ListELResolver();
        ELContext context = new ELContextImpl();

        List<String> list = new ArrayList<String>();
        list.add("key");
        resolver.getValue(context, list, "key");
    }

    /**
     * Tests that the key is out of bounds and null will be returned.
     */
    @Test
    public void testGetValue05() {
        ListELResolver resolver = new ListELResolver();
        ELContext context = new ELContextImpl();

        List<String> list = new ArrayList<String>();
        list.add("key");
        Object result = resolver.getValue(context, list, new Integer(1));

        Assert.assertNull(result);
        Assert.assertTrue(context.isPropertyResolved());

        result = resolver.getValue(context, list, new Integer(-1));

        Assert.assertNull(result);
        Assert.assertTrue(context.isPropertyResolved());
    }

    /**
     * Tests that a null context results in an NPE as per EL Javadoc.
     */
    @Test(expected = NullPointerException.class)
    public void testGetType01() {
        ListELResolver resolver = new ListELResolver();
        resolver.getType(null, new Object(), new Object());
    }

    /**
     * Tests that a valid property is not resolved if base is not List.
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
        ListELResolver resolver = new ListELResolver();
        ELContext context = new ELContextImpl();

        List<String> list = new ArrayList<String>();
        list.add("key");
        Class<?> result = resolver.getType(context, list, new Integer(0));

        Assert.assertEquals(Object.class, result);
        Assert.assertTrue(context.isPropertyResolved());
    }

    /**
     * Tests that the key is out of bounds and exception will be thrown.
     */
    @Test(expected = PropertyNotFoundException.class)
    public void testGetType04() {
        ListELResolver resolver = new ListELResolver();
        ELContext context = new ELContextImpl();

        List<String> list = new ArrayList<String>();
        list.add("key");
        resolver.getType(context, list, new Integer(1));
    }

    /**
     * Tests that a null context results in an NPE as per EL Javadoc.
     */
    @Test(expected = NullPointerException.class)
    public void testSetValue01() {
        ListELResolver resolver = new ListELResolver();
        resolver.setValue(null, new Object(), new Object(), new Object());
    }

    /**
     * Tests that a valid property is not set if base is not List.
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
        ListELResolver resolver = new ListELResolver(true);
        ELContext context = new ELContextImpl();

        resolver.setValue(context, new ArrayList<Object>(), new Object(),
                new Object());
    }

    /**
     * Tests that a valid property is set.
     */
    @Test
    public void testSetValue04() {
        ListELResolver resolver = new ListELResolver();
        ELContext context = new ELContextImpl();

        List<String> list = new ArrayList<String>();
        list.add("value");
        resolver.setValue(context, list, new Integer(0), "value");

        Assert.assertEquals("value",
                resolver.getValue(context, list, new Integer(0)));
        Assert.assertTrue(context.isPropertyResolved());
    }

    /**
     * Tests that an exception is thrown when the list is not modifiable.
     */
    @Test(expected = PropertyNotWritableException.class)
    public void testSetValue05() {
        ListELResolver resolver = new ListELResolver();
        ELContext context = new ELContextImpl();

        List<Object> list = Collections
                .unmodifiableList(new ArrayList<Object>());
        resolver.setValue(context, list, new Integer(0), "value");
    }

    /**
     * Tests a coercion cannot be performed as the key is not integer.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSetValue06() {
        ListELResolver resolver = new ListELResolver();
        ELContext context = new ELContextImpl();

        List<String> list = new ArrayList<String>();
        list.add("key");
        resolver.setValue(context, list, "key", "value");
    }

    /**
     * Tests that the key is out of bounds and exception will be thrown.
     */
    @Test(expected = PropertyNotFoundException.class)
    public void testSetValue07() {
        ListELResolver resolver = new ListELResolver();
        ELContext context = new ELContextImpl();

        List<String> list = new ArrayList<String>();
        list.add("key");
        resolver.setValue(context, list, new Integer(1), "value");
    }

    /**
     * Tests that a null context results in an NPE as per EL Javadoc.
     */
    @Test(expected = NullPointerException.class)
    public void testIsReadOnly01() {
        ListELResolver resolver = new ListELResolver();
        resolver.isReadOnly(null, new Object(), new Object());
    }

    /**
     * Tests that the propertyResolved is false if base is not List.
     */
    @Test
    public void testIsReadOnly02() {
        ListELResolver resolver = new ListELResolver();
        ELContext context = new ELContextImpl();

        boolean result = resolver.isReadOnly(context, new Object(),
                new Object());

        Assert.assertFalse(result);
        Assert.assertFalse(context.isPropertyResolved());

        resolver = new ListELResolver(true);

        result = resolver.isReadOnly(context, new Object(), new Object());

        Assert.assertTrue(result);
        Assert.assertFalse(context.isPropertyResolved());
    }

    /**
     * Tests that if the ListELResolver is constructed with readOnly the method
     * will return always true, otherwise false.
     */
    @Test
    public void testIsReadOnly03() {
        ListELResolver resolver = new ListELResolver();
        ELContext context = new ELContextImpl();

        List<String> list = new ArrayList<String>();
        list.add("key");
        boolean result = resolver.isReadOnly(context, list, new Integer(0));

        Assert.assertFalse(result);
        Assert.assertTrue(context.isPropertyResolved());

        resolver = new ListELResolver(true);

        result = resolver.isReadOnly(context, list, new Integer(0));

        Assert.assertTrue(result);
        Assert.assertTrue(context.isPropertyResolved());
    }

    /**
     * Tests that the readOnly is true always when the map is not modifiable.
     */
    @Test
    public void testIsReadOnly04() {
        ListELResolver resolver = new ListELResolver();
        ELContext context = new ELContextImpl();

        List<String> list = new ArrayList<String>();
        list.add("key");
        List<String> unmodifiableList = Collections.unmodifiableList(list);
        boolean result = resolver.isReadOnly(context, unmodifiableList,
                new Integer(0));

        Assert.assertTrue(result);
        Assert.assertTrue(context.isPropertyResolved());
    }

    /**
     * Tests that the key is out of bounds and exception will be thrown.
     */
    @Test(expected = PropertyNotFoundException.class)
    public void testIsReadOnly05() {
        ListELResolver resolver = new ListELResolver();
        ELContext context = new ELContextImpl();

        List<String> list = new ArrayList<String>();
        list.add("key");
        resolver.isReadOnly(context, list, new Integer(1));
    }

    /**
     * Tests that a result is returned even when a coercion cannot be performed.
     */
    @Test
    public void testIsReadOnly06() {
        ListELResolver resolver = new ListELResolver();
        ELContext context = new ELContextImpl();

        List<String> list = new ArrayList<String>();
        list.add("key");
        boolean result = resolver.isReadOnly(context, list, "key");

        Assert.assertFalse(result);
        Assert.assertTrue(context.isPropertyResolved());

        resolver = new ListELResolver(true);

        result = resolver.isReadOnly(context, list, "key");

        Assert.assertTrue(result);
        Assert.assertTrue(context.isPropertyResolved());
    }

    private void doNegativeTest(Object base, Object trigger,
            MethodUnderTest method, boolean checkResult) {
        ListELResolver resolver = new ListELResolver();
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
