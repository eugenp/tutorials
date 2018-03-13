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
import java.util.Enumeration;
import java.util.Iterator;
import java.util.ListResourceBundle;
import java.util.ResourceBundle;

import org.junit.Assert;
import org.junit.Test;

import org.apache.jasper.el.ELContextImpl;

public class TestResourceBundleELResolver {

    @Test
    public void bug53001() {
        ExpressionFactory factory = ExpressionFactory.newInstance();
        ELContext context = new ELContextImpl();

        ResourceBundle rb = new TesterResourceBundle();

        ValueExpression var = factory.createValueExpression(rb,
                ResourceBundle.class);
        context.getVariableMapper().setVariable("rb", var);

        ValueExpression ve = factory.createValueExpression(context,
                "${rb.keys}", String.class);

        MethodExpression me = factory.createMethodExpression(context,
                "${rb.getKeys()}", Enumeration.class, null);

        // Ensure we are specification compliant
        String result1 = (String) ve.getValue(context);
        Assert.assertEquals("???keys???", result1);

        // Check that the method expression does return the keys
        Object result2 = me.invoke(context, null);
        Assert.assertTrue(result2 instanceof Enumeration);
        @SuppressWarnings("unchecked")
        Enumeration<String> e = (Enumeration<String>) result2;

        Assert.assertTrue(e.hasMoreElements());
        String element = e.nextElement();
        if ("key1".equals(element)) {
            Assert.assertEquals("key1", element);
            Assert.assertTrue(e.hasMoreElements());
            Assert.assertEquals("key2", e.nextElement());
            Assert.assertFalse(e.hasMoreElements());
        } else {
            Assert.assertEquals("key2", element);
            Assert.assertTrue(e.hasMoreElements());
            Assert.assertEquals("key1", e.nextElement());
            Assert.assertFalse(e.hasMoreElements());
        }
    }

    /**
     * Tests that a null context results in an NPE as per EL Javadoc.
     */
    @Test(expected = NullPointerException.class)
    public void testGetValue01() {
        ResourceBundleELResolver resolver = new ResourceBundleELResolver();
        resolver.getValue(null, new Object(), new Object());
    }

    /**
     * Tests that a valid property is not resolved if base is not
     * ResourceBundle.
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
        ResourceBundleELResolver resolver = new ResourceBundleELResolver();
        ELContext context = new ELContextImpl();

        ResourceBundle resourceBundle = new TesterResourceBundle();
        Object result = resolver.getValue(context, resourceBundle, "key1");

        Assert.assertEquals("value1", result);
        Assert.assertTrue(context.isPropertyResolved());

        result = resolver.getValue(context, resourceBundle, "unknown-key");

        Assert.assertEquals("???unknown-key???", result);
        Assert.assertTrue(context.isPropertyResolved());

        result = resolver.getValue(context, resourceBundle, null);

        Assert.assertNull(result);
        Assert.assertTrue(context.isPropertyResolved());
    }

    /**
     * Tests that a null context results in an NPE as per EL Javadoc.
     */
    @Test(expected = NullPointerException.class)
    public void testGetType01() {
        ResourceBundleELResolver resolver = new ResourceBundleELResolver();
        resolver.getType(null, new Object(), new Object());
    }

    /**
     * Tests that a valid property is not resolved if base is not
     * ResourceBundle.
     */
    @Test
    public void testGetType02() {
        doNegativeTest(new Object(), new Object(), MethodUnderTest.GET_TYPE,
                true);
    }

    /**
     * Tests that null will be returned when base is ResourceBundle. Checks that
     * the propertyResolved is true.
     */
    @Test
    public void testGetType03() {
        ResourceBundleELResolver resolver = new ResourceBundleELResolver();
        ELContext context = new ELContextImpl();

        ResourceBundle resourceBundle = new TesterResourceBundle();
        Class<?> result = resolver.getType(context, resourceBundle, "key1");

        Assert.assertNull(result);
        Assert.assertTrue(context.isPropertyResolved());
    }

    /**
     * Tests that a null context results in an NPE as per EL Javadoc.
     */
    @Test(expected = NullPointerException.class)
    public void testSetValue01() {
        ResourceBundleELResolver resolver = new ResourceBundleELResolver();
        resolver.setValue(null, new Object(), new Object(), new Object());
    }

    /**
     * Tests that a valid property is not set if base is not ResourceBundle.
     */
    @Test
    public void testSetValue02() {
        doNegativeTest(new Object(), new Object(), MethodUnderTest.SET_VALUE,
                false);
    }

    /**
     * Tests that an exception is thrown because the resolver is read only.
     */
    @Test(expected = PropertyNotWritableException.class)
    public void testSetValue03() {
        ResourceBundleELResolver resolver = new ResourceBundleELResolver();
        ELContext context = new ELContextImpl();

        ResourceBundle resourceBundle = new TesterResourceBundle();
        resolver.setValue(context, resourceBundle, new Object(), new Object());
    }

    /**
     * Tests that a null context results in an NPE as per EL Javadoc.
     */
    @Test(expected = NullPointerException.class)
    public void testIsReadOnly01() {
        ResourceBundleELResolver resolver = new ResourceBundleELResolver();
        resolver.isReadOnly(null, new Object(), new Object());
    }

    /**
     * Tests that the propertyResolved is false and readOnly is false if base is
     * not ResourceBundle.
     */
    @Test
    public void testIsReadOnly02() {
        ResourceBundleELResolver resolver = new ResourceBundleELResolver();
        ELContext context = new ELContextImpl();

        boolean result = resolver.isReadOnly(context, new Object(),
                new Object());

        Assert.assertFalse(result);
        Assert.assertFalse(context.isPropertyResolved());
    }

    /**
     * Tests that the readOnly is true always when the base is ResourceBundle.
     */
    @Test
    public void testIsReadOnly03() {
        ResourceBundleELResolver resolver = new ResourceBundleELResolver();
        ELContext context = new ELContextImpl();

        ResourceBundle resourceBundle = new TesterResourceBundle();
        boolean result = resolver.isReadOnly(context, resourceBundle,
                new Object());

        Assert.assertTrue(result);
        Assert.assertTrue(context.isPropertyResolved());
    }

    /**
     * Tests that a valid FeatureDescriptors are not returned if base is not
     * ResourceBundle.
     */
    @Test
    public void testGetFeatureDescriptors01() {
        ResourceBundleELResolver resolver = new ResourceBundleELResolver();
        ELContext context = new ELContextImpl();

        @SuppressWarnings("unchecked")
        Iterator<FeatureDescriptor> result = resolver.getFeatureDescriptors(
                context, new Object());

        Assert.assertNull(result);
    }

    /**
     * Tests that a valid FeatureDescriptors are returned.
     */
    @Test
    public void testGetFeatureDescriptors02() {
        ResourceBundleELResolver resolver = new ResourceBundleELResolver();
        ELContext context = new ELContextImpl();

        ResourceBundle resourceBundle = new TesterResourceBundle(
                new Object[][] { { "key", "value" } });
        @SuppressWarnings("unchecked")
        Iterator<FeatureDescriptor> result = resolver.getFeatureDescriptors(
                context, resourceBundle);

        while (result.hasNext()) {
            FeatureDescriptor featureDescriptor = result.next();
            Assert.assertEquals("key", featureDescriptor.getDisplayName());
            Assert.assertEquals("key", featureDescriptor.getName());
            Assert.assertEquals("", featureDescriptor.getShortDescription());
            Assert.assertFalse(featureDescriptor.isExpert());
            Assert.assertFalse(featureDescriptor.isHidden());
            Assert.assertTrue(featureDescriptor.isPreferred());
            Assert.assertEquals(String.class,
                    featureDescriptor.getValue(ELResolver.TYPE));
            Assert.assertEquals(Boolean.TRUE, featureDescriptor
                    .getValue(ELResolver.RESOLVABLE_AT_DESIGN_TIME));
        }
    }

    private static class TesterResourceBundle extends ListResourceBundle {

        public TesterResourceBundle() {
            this(new Object[][] { { "key1", "value1" }, { "key2", "value2" } });
        }

        public TesterResourceBundle(Object[][] contents) {
            this.contents = contents;
        }

        @Override
        protected Object[][] getContents() {
            return contents;
        }

        private Object[][] contents;
    }

    private void doNegativeTest(Object base, Object trigger,
            MethodUnderTest method, boolean checkResult) {
        ResourceBundleELResolver resolver = new ResourceBundleELResolver();
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
