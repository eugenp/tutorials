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
import java.beans.PropertyDescriptor;
import java.util.Iterator;

import org.junit.Assert;
import org.junit.Test;

import org.apache.jasper.el.ELContextImpl;

public class TestBeanELResolver {

    private static final String METHOD01_NAME = "toString";
    private static final String METHOD02_NAME = "<init>";
    private static final String METHOD03_NAME = "nonExistingMethod";
    private static final String BEAN_NAME = "test";
    private static final String PROPERTY01_NAME = "valueA";
    private static final String PROPERTY02_NAME = "valueB";
    private static final String PROPERTY03_NAME = "name";
    private static final String PROPERTY_VALUE = "test1";

    @Test
    public void testBug53421() {
        ExpressionFactory factory = ExpressionFactory.newInstance();
        ELContext context = new ELContextImpl();

        Bean bean = new Bean();

        ValueExpression varBean =
            factory.createValueExpression(bean, Bean.class);
        context.getVariableMapper().setVariable("bean", varBean);


        ValueExpression ve = factory.createValueExpression(
                context, "${bean.valueA}", String.class);
        Exception e = null;
        try {
            ve.getValue(context);
        } catch (PropertyNotFoundException pnfe) {
            e = pnfe;
        }
        Assert.assertTrue("Wrong exception type",
                e instanceof PropertyNotFoundException);
        String type = Bean.class.getName();
        String msg = e.getMessage();
        Assert.assertTrue("No reference to type [" + type +
                "] where property cannot be found in [" + msg + "]",
                msg.contains(type));
    }

    /**
     * Tests that a null context results in an NPE as per EL Javadoc.
     */
    @Test(expected = NullPointerException.class)
    public void testGetType01() {
        BeanELResolver resolver = new BeanELResolver();
        resolver.getType(null, new Object(), new Object());
    }

    /**
     * Tests that a valid property is not resolved if base is null.
     */
    @Test
    public void testGetType02() {
        doNegativeTest(null, new Object(), MethodUnderTest.GET_TYPE, true);
    }

    /**
     * Tests that a valid property is resolved.
     */
    @Test
    public void testGetType03() {
        BeanELResolver resolver = new BeanELResolver();
        ELContext context = new ELContextImpl();

        Class<?> result = resolver.getType(context, new Bean(), PROPERTY01_NAME);

        Assert.assertEquals(String.class, result);
        Assert.assertTrue(context.isPropertyResolved());
    }

    /**
     * Tests that an exception will be thrown when the property does not exist.
     */
    @Test(expected = PropertyNotFoundException.class)
    public void testGetType04() {
        BeanELResolver resolver = new BeanELResolver();
        ELContext context = new ELContextImpl();

        resolver.getType(context, new Bean(), PROPERTY02_NAME);
    }

    /**
     * Tests that an exception will be thrown when a coercion cannot be
     * performed.
     */
    @Test(expected = PropertyNotFoundException.class)
    public void testGetType05() {
        BeanELResolver resolver = new BeanELResolver();
        ELContext context = new ELContextImpl();

        resolver.getType(context, new Bean(), new Object());
    }

    /**
     * Tests that a null context results in an NPE as per EL Javadoc.
     */
    @Test(expected = NullPointerException.class)
    public void testGetValue01() {
        BeanELResolver resolver = new BeanELResolver();
        resolver.getValue(null, new Object(), new Object());
    }

    /**
     * Tests that a valid property is not resolved if base is null.
     */
    @Test
    public void testGetValue02() {
        doNegativeTest(null, new Object(), MethodUnderTest.GET_VALUE, true);
    }

    /**
     * Tests that a valid property is resolved.
     */
    @Test
    public void testGetValue03() {
        BeanELResolver resolver = new BeanELResolver();
        ELContext context = new ELContextImpl();

        Object result = resolver.getValue(context, new TesterBean(BEAN_NAME), PROPERTY03_NAME);

        Assert.assertEquals(BEAN_NAME, result);
        Assert.assertTrue(context.isPropertyResolved());
    }

    /**
     * Tests that an exception will be thrown when the property does not exist.
     */
    @Test(expected = PropertyNotFoundException.class)
    public void testGetValue04() {
        BeanELResolver resolver = new BeanELResolver();
        ELContext context = new ELContextImpl();

        resolver.getValue(context, new Bean(), PROPERTY02_NAME);
    }

    /**
     * Tests that an exception will be thrown when a coercion cannot be
     * performed.
     */
    @Test(expected = PropertyNotFoundException.class)
    public void testGetValue05() {
        BeanELResolver resolver = new BeanELResolver();
        ELContext context = new ELContextImpl();

        resolver.getValue(context, new Bean(), new Object());
    }

    /**
     * Tests that an exception will be thrown when the property is not readable.
     */
    @Test(expected = PropertyNotFoundException.class)
    public void testGetValue06() {
        BeanELResolver resolver = new BeanELResolver();
        ELContext context = new ELContextImpl();

        resolver.getValue(context, new Bean(), PROPERTY01_NAME);
    }

    /**
     * Tests that getter method throws exception which should be propagated.
     */
    @Test(expected = ELException.class)
    public void testGetValue07() {
        BeanELResolver resolver = new BeanELResolver();
        ELContext context = new ELContextImpl();

        resolver.getValue(context, new TesterBean(BEAN_NAME), PROPERTY01_NAME);
    }

    /**
     * Tests that a null context results in an NPE as per EL Javadoc.
     */
    @Test(expected = NullPointerException.class)
    public void testSetValue01() {
        BeanELResolver resolver = new BeanELResolver();
        resolver.setValue(null, new Object(), new Object(), new Object());
    }

    /**
     * Tests that a valid property is not resolved if base is null.
     */
    @Test
    public void testSetValue02() {
        doNegativeTest(null, new Object(), MethodUnderTest.SET_VALUE, true);
    }

    /**
     * Tests that an exception is thrown when readOnly is true.
     */
    @Test(expected = PropertyNotWritableException.class)
    public void testSetValue03() {
        BeanELResolver resolver = new BeanELResolver(true);
        ELContext context = new ELContextImpl();

        resolver.setValue(context, new Bean(), new Object(), new Object());
    }

    /**
     * Tests that a valid property is resolved.
     */
    @Test
    public void testSetValue04() {
        BeanELResolver resolver = new BeanELResolver();
        ELContext context = new ELContextImpl();

        TesterBean bean = new TesterBean(BEAN_NAME);
        resolver.setValue(context, bean, PROPERTY03_NAME, PROPERTY_VALUE);

        Assert.assertEquals(PROPERTY_VALUE, resolver.getValue(context, bean, PROPERTY03_NAME));
        Assert.assertTrue(context.isPropertyResolved());
    }

    /**
     * Tests that an exception will be thrown when a coercion cannot be
     * performed.
     */
    @Test(expected = PropertyNotFoundException.class)
    public void testSetValue05() {
        BeanELResolver resolver = new BeanELResolver();
        ELContext context = new ELContextImpl();

        resolver.setValue(context, new Bean(), new Object(), PROPERTY_VALUE);
    }

    /**
     * Tests that an exception will be thrown when the property does not exist.
     */
    @Test(expected = PropertyNotFoundException.class)
    public void testSetValue06() {
        BeanELResolver resolver = new BeanELResolver();
        ELContext context = new ELContextImpl();

        resolver.setValue(context, new Bean(), PROPERTY02_NAME, PROPERTY_VALUE);
    }

    /**
     * Tests that an exception will be thrown when the property does not have
     * setter method.
     */
    @Test(expected = PropertyNotFoundException.class)
    public void testSetValue07() {
        BeanELResolver resolver = new BeanELResolver();
        ELContext context = new ELContextImpl();

        resolver.setValue(context, new TesterBean(BEAN_NAME), PROPERTY01_NAME, PROPERTY_VALUE);
    }

    /**
     * Tests that a null context results in an NPE as per EL Javadoc.
     */
    @Test(expected = NullPointerException.class)
    public void testIsReadOnly01() {
        BeanELResolver resolver = new BeanELResolver();
        resolver.isReadOnly(null, new Object(), new Object());
    }

    /**
     * Tests that the propertyResolved is false if base is null.
     */
    @Test
    public void testIsReadOnly02() {
        BeanELResolver resolver = new BeanELResolver();
        ELContext context = new ELContextImpl();

        resolver.isReadOnly(context, null, new Object());

        Assert.assertFalse(context.isPropertyResolved());

        resolver = new BeanELResolver(true);

        resolver.isReadOnly(context, null, new Object());

        Assert.assertFalse(context.isPropertyResolved());
    }

    /**
     * Tests that if the BeanELResolver is constructed with readOnly the method
     * will return always true.
     */
    @Test
    public void testIsReadOnly03() {
        BeanELResolver resolver = new BeanELResolver();
        ELContext context = new ELContextImpl();

        boolean result = resolver.isReadOnly(context, new TesterBean(BEAN_NAME), PROPERTY03_NAME);

        Assert.assertFalse(result);
        Assert.assertTrue(context.isPropertyResolved());

        resolver = new BeanELResolver(true);

        result = resolver.isReadOnly(context, new TesterBean(BEAN_NAME), PROPERTY03_NAME);

        Assert.assertTrue(result);
        Assert.assertTrue(context.isPropertyResolved());
    }

    /**
     * Tests that an exception is thrown when a coercion cannot be performed.
     */
    @Test(expected = PropertyNotFoundException.class)
    public void testIsReadOnly04() {
        BeanELResolver resolver = new BeanELResolver();
        ELContext context = new ELContextImpl();

        resolver.isReadOnly(context, new TesterBean(BEAN_NAME), new Integer(0));
    }

    /**
     * Tests that an exception will be thrown when the property does not exist.
     */
    @Test(expected = PropertyNotFoundException.class)
    public void testIsReadOnly05() {
        BeanELResolver resolver = new BeanELResolver();
        ELContext context = new ELContextImpl();

        resolver.isReadOnly(context, new Bean(), PROPERTY02_NAME);
    }

    /**
     * Tests that true will be returned when the property does not have setter
     * method.
     */
    @Test
    public void testIsReadOnly06() {
        BeanELResolver resolver = new BeanELResolver();
        ELContext context = new ELContextImpl();

        boolean result = resolver.isReadOnly(context, new TesterBean(BEAN_NAME), PROPERTY01_NAME);

        Assert.assertTrue(result);
        Assert.assertTrue(context.isPropertyResolved());
    }

    /**
     * Tests that a valid FeatureDescriptors are not returned if base is not
     * Map.
     */
    @Test
    public void testGetFeatureDescriptors01() {
        BeanELResolver resolver = new BeanELResolver();
        ELContext context = new ELContextImpl();

        Iterator<FeatureDescriptor> result = resolver.getFeatureDescriptors(context, null);

        Assert.assertNull(result);
    }

    /**
     * Tests that a valid FeatureDescriptors are returned.
     */
    @Test
    public void testGetFeatureDescriptors02() {
        BeanELResolver resolver = new BeanELResolver();
        ELContext context = new ELContextImpl();

        Iterator<FeatureDescriptor> result = resolver.getFeatureDescriptors(context, new Bean());

        while (result.hasNext()) {
            PropertyDescriptor featureDescriptor = (PropertyDescriptor) result.next();
            Assert.assertEquals(featureDescriptor.getPropertyType(),
                    featureDescriptor.getValue(ELResolver.TYPE));
            Assert.assertEquals(Boolean.TRUE,
                    featureDescriptor.getValue(ELResolver.RESOLVABLE_AT_DESIGN_TIME));
        }
    }

    /**
     * Tests that a null context results in an NPE as per EL Javadoc.
     */
    @Test(expected = NullPointerException.class)
    public void testInvoke01() {
        BeanELResolver resolver = new BeanELResolver();
        resolver.invoke(null, new Object(), new Object(), new Class<?>[0], new Object[0]);
    }

    /**
     * Tests that a valid property is not resolved if base is null.
     */
    @Test
    public void testInvoke02() {
        doNegativeTest(null, new Object(), MethodUnderTest.INVOKE, true);
    }

    /**
     * Tests a method invocation.
     */
    @Test
    public void testInvoke03() {
        BeanELResolver resolver = new BeanELResolver();
        ELContext context = new ELContextImpl();

        Object result = resolver.invoke(context, new TesterBean(BEAN_NAME), METHOD01_NAME,
                new Class<?>[] {}, new Object[] {});

        Assert.assertEquals(BEAN_NAME, result);
        Assert.assertTrue(context.isPropertyResolved());
    }

    /**
     * Tests that the method name cannot be coerced to String.
     */
    @Test
    public void testInvoke04() {
        doNegativeTest(new Bean(), null, MethodUnderTest.INVOKE, true);
    }

    /**
     * Tests that a call to &lt;init&gt; as a method name will throw an exception.
     */
    @Test(expected = MethodNotFoundException.class)
    public void testInvoke05() {
        BeanELResolver resolver = new BeanELResolver();
        ELContext context = new ELContextImpl();

        resolver.invoke(context, new TesterBean(BEAN_NAME), METHOD02_NAME, new Class<?>[] {},
                new Object[] {});
    }

    /**
     * Tests that a call to a non existing method will throw an exception.
     */
    @Test(expected = MethodNotFoundException.class)
    public void testInvoke06() {
        BeanELResolver resolver = new BeanELResolver();
        ELContext context = new ELContextImpl();

        resolver.invoke(context, new TesterBean(BEAN_NAME), METHOD03_NAME, new Class<?>[] {},
                new Object[] {});
    }

    private static class Bean {

        @SuppressWarnings("unused")
        public void setValueA(String valueA) {
            // NOOP
        }
    }

    private void doNegativeTest(Object base, Object trigger, MethodUnderTest method,
            boolean checkResult) {
        BeanELResolver resolver = new BeanELResolver();
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
        case INVOKE: {
            result = resolver.invoke(context, base, trigger, new Class<?>[0], new Object[0]);
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
        GET_VALUE, SET_VALUE, GET_TYPE, INVOKE
    }

}
