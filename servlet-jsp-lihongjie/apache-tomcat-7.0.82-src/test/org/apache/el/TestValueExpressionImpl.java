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

package org.apache.el;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.el.ValueReference;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import org.apache.jasper.el.ELContextImpl;

public class TestValueExpressionImpl {

    @Test
    public void testGetValueReference() {
        ExpressionFactory factory = ExpressionFactory.newInstance();
        ELContext context = new ELContextImpl();

        TesterBeanB beanB = new TesterBeanB();
        beanB.setName("Tomcat");
        ValueExpression var =
            factory.createValueExpression(beanB, TesterBeanB.class);
        context.getVariableMapper().setVariable("beanB", var);

        ValueExpression ve = factory.createValueExpression(
                context, "${beanB.name}", String.class);

        // First check the basics work
        String result = (String) ve.getValue(context);
        assertEquals("Tomcat", result);

        // Now check the value reference
        ValueReference vr = ve.getValueReference(context);
        assertNotNull(vr);

        assertEquals(beanB, vr.getBase());
        assertEquals("name", vr.getProperty());
    }

    @Test
    public void testGetValueReferenceVariable() {
        ExpressionFactory factory = ExpressionFactory.newInstance();
        ELContext context = new ELContextImpl();

        TesterBeanB beanB = new TesterBeanB();
        beanB.setName("Tomcat");
        ValueExpression var =
            factory.createValueExpression(beanB, TesterBeanB.class);
        context.getVariableMapper().setVariable("beanB", var);

        ValueExpression var2 = factory.createValueExpression(
                context, "${beanB.name}", String.class);

        context.getVariableMapper().setVariable("foo", var2);

        ValueExpression ve = factory.createValueExpression(
                context, "${foo}", ValueExpression.class);


        // Now check the value reference
        ValueReference vr = ve.getValueReference(context);
        assertNotNull(vr);

        assertEquals(beanB, vr.getBase());
        assertEquals("name", vr.getProperty());
    }

    @Test
    public void testBug49345() {
        ExpressionFactory factory = ExpressionFactory.newInstance();
        ELContext context = new ELContextImpl();

        TesterBeanA beanA = new TesterBeanA();
        TesterBeanB beanB = new TesterBeanB();
        beanB.setName("Tomcat");
        beanA.setBean(beanB);

        ValueExpression var =
            factory.createValueExpression(beanA, TesterBeanA.class);
        context.getVariableMapper().setVariable("beanA", var);

        ValueExpression ve = factory.createValueExpression(
                context, "${beanA.bean.name}", String.class);

        // First check the basics work
        String result = (String) ve.getValue(context);
        assertEquals("Tomcat", result);

        // Now check the value reference
        ValueReference vr = ve.getValueReference(context);
        assertNotNull(vr);

        assertEquals(beanB, vr.getBase());
        assertEquals("name", vr.getProperty());
    }

    @Test
    public void testBug50105() {
        ExpressionFactory factory = ExpressionFactory.newInstance();
        ELContext context = new ELContextImpl();

        TesterEnum testEnum = TesterEnum.APPLE;

        ValueExpression var =
            factory.createValueExpression(testEnum, TesterEnum.class);
        context.getVariableMapper().setVariable("testEnum", var);

        // When coercing an Enum to a String, name() should always be used.
        ValueExpression ve1 = factory.createValueExpression(
                context, "${testEnum}", String.class);
        String result1 = (String) ve1.getValue(context);
        assertEquals("APPLE", result1);

        ValueExpression ve2 = factory.createValueExpression(
                context, "foo${testEnum}bar", String.class);
        String result2 = (String) ve2.getValue(context);
        assertEquals("fooAPPLEbar", result2);
    }

    @Test
    public void testBug51177ObjectMap() {
        ExpressionFactory factory = ExpressionFactory.newInstance();
        ELContext context = new ELContextImpl();

        Object o1 = "String value";
        Object o2 = Integer.valueOf(32);

        Map<Object,Object> map = new HashMap<Object,Object>();
        map.put("key1", o1);
        map.put("key2", o2);

        ValueExpression var =
            factory.createValueExpression(map, Map.class);
        context.getVariableMapper().setVariable("map", var);

        ValueExpression ve1 = factory.createValueExpression(
                context, "${map.key1}", Object.class);
        ve1.setValue(context, o2);
        assertEquals(o2, ve1.getValue(context));

        ValueExpression ve2 = factory.createValueExpression(
                context, "${map.key2}", Object.class);
        ve2.setValue(context, o1);
        assertEquals(o1, ve2.getValue(context));
    }

    @Test
    public void testBug51177ObjectList() {
        ExpressionFactory factory = ExpressionFactory.newInstance();
        ELContext context = new ELContextImpl();

        Object o1 = "String value";
        Object o2 = Integer.valueOf(32);

        List<Object> list = new ArrayList<Object>();
        list.add(0, o1);
        list.add(1, o2);

        ValueExpression var =
            factory.createValueExpression(list, List.class);
        context.getVariableMapper().setVariable("list", var);

        ValueExpression ve1 = factory.createValueExpression(
                context, "${list[0]}", Object.class);
        ve1.setValue(context, o2);
        assertEquals(o2, ve1.getValue(context));

        ValueExpression ve2 = factory.createValueExpression(
                context, "${list[1]}", Object.class);
        ve2.setValue(context, o1);
        assertEquals(o1, ve2.getValue(context));
    }


    /**
     * Test returning an empty list as a bean property.
     */
    @Test
    public void testBug51544Bean() throws Exception {
        ExpressionFactory factory = ExpressionFactory.newInstance();
        ELContext context = new ELContextImpl();

        TesterBeanA beanA = new TesterBeanA();
        beanA.setValList(Collections.emptyList());

        ValueExpression var =
            factory.createValueExpression(beanA, TesterBeanA.class);
        context.getVariableMapper().setVariable("beanA", var);

        ValueExpression ve = factory.createValueExpression(
                context, "${beanA.valList.size()}", Integer.class);

        Integer result = (Integer) ve.getValue(context);
        assertEquals(Integer.valueOf(0), result);
    }


    /**
     * Test using list directly as variable.
     */
    @Test
    public void testBug51544Direct() throws Exception {
        ExpressionFactory factory = ExpressionFactory.newInstance();
        ELContext context = new ELContextImpl();

        List<?> list = Collections.emptyList();

        ValueExpression var =
            factory.createValueExpression(list, List.class);
        context.getVariableMapper().setVariable("list", var);

        ValueExpression ve = factory.createValueExpression(
                context, "${list.size()}", Integer.class);

        Integer result = (Integer) ve.getValue(context);
        assertEquals(Integer.valueOf(0), result);
    }
}
