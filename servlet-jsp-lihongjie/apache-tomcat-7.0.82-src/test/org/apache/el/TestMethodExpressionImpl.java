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

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;
import javax.el.ValueExpression;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import org.apache.jasper.el.ELContextImpl;

public class TestMethodExpressionImpl {

    private static final String BUG53792 = "TEST_PASS";

    private ExpressionFactory factory;
    private ELContext context;
    private TesterBeanB beanB;

    @Before
    public void setUp() {
        factory = ExpressionFactory.newInstance();
        context = new ELContextImpl();

        TesterBeanA beanA = new TesterBeanA();
        beanA.setName("A");
        context.getVariableMapper().setVariable("beanA",
                factory.createValueExpression(beanA, TesterBeanA.class));

        TesterBeanAA beanAA = new TesterBeanAA();
        beanAA.setName("AA");
        context.getVariableMapper().setVariable("beanAA",
                factory.createValueExpression(beanAA, TesterBeanAA.class));

        TesterBeanAAA beanAAA = new TesterBeanAAA();
        beanAAA.setName("AAA");
        context.getVariableMapper().setVariable("beanAAA",
                factory.createValueExpression(beanAAA, TesterBeanAAA.class));

        beanB = new TesterBeanB();
        beanB.setName("B");
        context.getVariableMapper().setVariable("beanB",
                factory.createValueExpression(beanB, TesterBeanB.class));

        TesterBeanBB beanBB = new TesterBeanBB();
        beanBB.setName("BB");
        context.getVariableMapper().setVariable("beanBB",
                factory.createValueExpression(beanBB, TesterBeanBB.class));

        TesterBeanBBB beanBBB = new TesterBeanBBB();
        beanBBB.setName("BBB");
        context.getVariableMapper().setVariable("beanBBB",
                factory.createValueExpression(beanBBB, TesterBeanBBB.class));

        TesterBeanC beanC = new TesterBeanC();
        context.getVariableMapper().setVariable("beanC",
                factory.createValueExpression(beanC, TesterBeanC.class));

        TesterBeanEnum beanEnum = new TesterBeanEnum();
        context.getVariableMapper().setVariable("beanEnum",
                factory.createValueExpression(beanEnum, TesterBeanEnum.class));
    }

    @Test
    public void testIsParametersProvided() {
        TesterBeanB beanB = new TesterBeanB();
        beanB.setName("Tomcat");
        ValueExpression var =
            factory.createValueExpression(beanB, TesterBeanB.class);
        context.getVariableMapper().setVariable("beanB", var);

        MethodExpression me1 = factory.createMethodExpression(
                context, "${beanB.getName}", String.class, new Class<?>[] {});
        MethodExpression me2 = factory.createMethodExpression(
                context, "${beanB.sayHello('JUnit')}", String.class,
                new Class<?>[] { String.class });

        assertFalse(me1.isParmetersProvided());
        assertTrue(me2.isParmetersProvided());
    }

    @Test
    public void testInvoke() {
        TesterBeanB beanB = new TesterBeanB();
        beanB.setName("B");

        context.getVariableMapper().setVariable("beanB",
                factory.createValueExpression(beanB, TesterBeanB.class));

        MethodExpression me1 = factory.createMethodExpression(
                context, "${beanB.getName}", String.class, new Class<?>[] {});
        MethodExpression me2 = factory.createMethodExpression(
                context, "${beanB.sayHello('JUnit')}", String.class,
                new Class<?>[] { String.class });
        MethodExpression me3 = factory.createMethodExpression(
                context, "${beanB.sayHello}", String.class,
                new Class<?>[] { String.class });

        assertEquals("B", me1.invoke(context, null));
        assertEquals("Hello JUnit from B", me2.invoke(context, null));
        assertEquals("Hello JUnit from B",
                me2.invoke(context, new Object[] { "JUnit2" }));
        assertEquals("Hello JUnit2 from B",
                me3.invoke(context, new Object[] { "JUnit2" }));
        assertEquals("Hello JUnit from B",
                me2.invoke(context, new Object[] { null }));
        assertEquals("Hello  from B",
                me3.invoke(context, new Object[] { null }));
    }

    @Test
    public void testInvokeWithSuper() {
        MethodExpression me = factory.createMethodExpression(context,
                "${beanA.setBean(beanBB)}", null ,
                new Class<?>[] { TesterBeanB.class });
        me.invoke(context, null);
        ValueExpression ve = factory.createValueExpression(context,
                "${beanA.bean.name}", String.class);
        Object r = ve.getValue(context);
        assertEquals("BB", r);
    }

    @Test
    public void testInvokeWithSuperABNoReturnTypeNoParamTypes() {
        MethodExpression me2 = factory.createMethodExpression(context,
                "${beanC.sayHello(beanA,beanB)}", null , null);
        Object r2 = me2.invoke(context, null);
        assertEquals("AB: Hello A from B", r2.toString());
    }

    @Test
    public void testInvokeWithSuperABReturnTypeNoParamTypes() {
        MethodExpression me3 = factory.createMethodExpression(context,
                "${beanC.sayHello(beanA,beanB)}", String.class , null);
        Object r3 = me3.invoke(context, null);
        assertEquals("AB: Hello A from B", r3.toString());
    }

    @Test
    public void testInvokeWithSuperABNoReturnTypeParamTypes() {
        MethodExpression me4 = factory.createMethodExpression(context,
                "${beanC.sayHello(beanA,beanB)}", null ,
                new Class<?>[] {TesterBeanA.class, TesterBeanB.class});
        Object r4 = me4.invoke(context, null);
        assertEquals("AB: Hello A from B", r4.toString());
    }

    @Test
    public void testInvokeWithSuperABReturnTypeParamTypes() {
        MethodExpression me5 = factory.createMethodExpression(context,
                "${beanC.sayHello(beanA,beanB)}", String.class ,
                new Class<?>[] {TesterBeanA.class, TesterBeanB.class});
        Object r5 = me5.invoke(context, null);
        assertEquals("AB: Hello A from B", r5.toString());
    }

    @Test
    public void testInvokeWithSuperABB() {
        MethodExpression me6 = factory.createMethodExpression(context,
                "${beanC.sayHello(beanA,beanBB)}", null , null);
        Object r6 = me6.invoke(context, null);
        assertEquals("ABB: Hello A from BB", r6.toString());
    }

    @Test
    public void testInvokeWithSuperABBB() {
        MethodExpression me7 = factory.createMethodExpression(context,
                "${beanC.sayHello(beanA,beanBBB)}", null , null);
        Object r7 = me7.invoke(context, null);
        assertEquals("ABB: Hello A from BBB", r7.toString());
    }

    @Test
    public void testInvokeWithSuperAAB() {
        MethodExpression me8 = factory.createMethodExpression(context,
                "${beanC.sayHello(beanAA,beanB)}", null , null);
        Object r8 = me8.invoke(context, null);
        assertEquals("AAB: Hello AA from B", r8.toString());
    }

    @Test
    public void testInvokeWithSuperAABB() {
        MethodExpression me9 = factory.createMethodExpression(context,
                "${beanC.sayHello(beanAA,beanBB)}", null , null);
        Exception e = null;
        try {
            me9.invoke(context, null);
        } catch (Exception e1) {
            e = e1;
        }
        // Expected to fail
        assertNotNull(e);
    }

    @Test
    public void testInvokeWithSuperAABBB() {
        // The Java compiler reports this as ambiguous. Using the parameter that
        // matches exactly seems reasonable to limit the scope of the method
        // search so the EL will find a match.
        MethodExpression me10 = factory.createMethodExpression(context,
                "${beanC.sayHello(beanAA,beanBBB)}", null , null);
        Object r10 = me10.invoke(context, null);
        assertEquals("AAB: Hello AA from BBB", r10.toString());
    }

    @Test
    public void testInvokeWithSuperAAAB() {
        MethodExpression me11 = factory.createMethodExpression(context,
                "${beanC.sayHello(beanAAA,beanB)}", null , null);
        Object r11 = me11.invoke(context, null);
        assertEquals("AAB: Hello AAA from B", r11.toString());
    }

    @Test
    public void testInvokeWithSuperAAABB() {
        // The Java compiler reports this as ambiguous. Using the parameter that
        // matches exactly seems reasonable to limit the scope of the method
        // search so the EL will find a match.
        MethodExpression me12 = factory.createMethodExpression(context,
                "${beanC.sayHello(beanAAA,beanBB)}", null , null);
        Object r12 = me12.invoke(context, null);
        assertEquals("ABB: Hello AAA from BB", r12.toString());
    }

    @Test
    public void testInvokeWithSuperAAABBB() {
        MethodExpression me13 = factory.createMethodExpression(context,
                "${beanC.sayHello(beanAAA,beanBBB)}", null , null);
        Exception e = null;
        try {
            me13.invoke(context, null);
        } catch (Exception e1) {
            e = e1;
        }
        // Expected to fail
        assertNotNull(e);
    }

    @Test
    public void testInvokeWithVarArgsAB() throws Exception {
        MethodExpression me1 = factory.createMethodExpression(context,
                "${beanC.sayHello(beanA,beanB,beanB)}", null , null);
        Exception e = null;
        try {
            me1.invoke(context, null);
        } catch (Exception e1) {
            e = e1;
        }
        // Expected to fail
        assertNotNull(e);
    }

    @Test
    public void testInvokeWithVarArgsABB() throws Exception {
        MethodExpression me2 = factory.createMethodExpression(context,
                "${beanC.sayHello(beanA,beanBB,beanBB)}", null , null);
        Object r2 = me2.invoke(context, null);
        assertEquals("ABB[]: Hello A from BB, BB", r2.toString());
    }

    @Test
    public void testInvokeWithVarArgsABBB() throws Exception {
        MethodExpression me3 = factory.createMethodExpression(context,
                "${beanC.sayHello(beanA,beanBBB,beanBBB)}", null , null);
        Object r3 = me3.invoke(context, null);
        assertEquals("ABB[]: Hello A from BBB, BBB", r3.toString());
    }

    @Test
    public void testInvokeWithVarArgsAAB() throws Exception {
        MethodExpression me4 = factory.createMethodExpression(context,
                "${beanC.sayHello(beanAA,beanB,beanB)}", null , null);
        Exception e = null;
        try {
            me4.invoke(context, null);
        } catch (Exception e1) {
            e = e1;
        }
        // Expected to fail
        assertNotNull(e);
    }

    @Test
    public void testInvokeWithVarArgsAABB() throws Exception {
        MethodExpression me5 = factory.createMethodExpression(context,
                "${beanC.sayHello(beanAA,beanBB,beanBB)}", null , null);
        Object r5 = me5.invoke(context, null);
        assertEquals("ABB[]: Hello AA from BB, BB", r5.toString());
    }

    @Test
    public void testInvokeWithVarArgsAABBB() throws Exception {
        MethodExpression me6 = factory.createMethodExpression(context,
                "${beanC.sayHello(beanAA,beanBBB,beanBBB)}", null , null);
        Object r6 = me6.invoke(context, null);
        assertEquals("ABB[]: Hello AA from BBB, BBB", r6.toString());
    }

    @Test
    public void testInvokeWithVarArgsAAAB() throws Exception {
        MethodExpression me7 = factory.createMethodExpression(context,
                "${beanC.sayHello(beanAAA,beanB,beanB)}", null , null);
        Exception e = null;
        try {
            me7.invoke(context, null);
        } catch (Exception e1) {
            e = e1;
        }
        // Expected to fail
        assertNotNull(e);
    }

    @Test
    public void testInvokeWithVarArgsAAABB() throws Exception {
        MethodExpression me8 = factory.createMethodExpression(context,
                "${beanC.sayHello(beanAAA,beanBB,beanBB)}", null , null);
        Object r8 = me8.invoke(context, null);
        assertEquals("ABB[]: Hello AAA from BB, BB", r8.toString());
    }

    @Test
    public void testInvokeWithVarArgsAAABBB() throws Exception {
        MethodExpression me9 = factory.createMethodExpression(context,
                "${beanC.sayHello(beanAAA,beanBBB,beanBBB)}", null , null);
        Object r9 = me9.invoke(context, null);
        assertEquals("ABB[]: Hello AAA from BBB, BBB", r9.toString());
    }

    /*
     * This is also tested implicitly in numerous places elsewhere in this
     * class.
     */
    @Test
    public void testBug49655() throws Exception {
        // This is the call the failed
        MethodExpression me = factory.createMethodExpression(context,
                "#{beanA.setName('New value')}", null, null);
        // The rest is to check it worked correctly
        me.invoke(context, null);
        ValueExpression ve = factory.createValueExpression(context,
                "#{beanA.name}", java.lang.String.class);
        assertEquals("New value", ve.getValue(context));
    }

    @Test
    public void testBugPrimitives() throws Exception {
        MethodExpression me = factory.createMethodExpression(context,
                "${beanA.setValLong(5)}", null, null);
        me.invoke(context, null);
        ValueExpression ve = factory.createValueExpression(context,
                "#{beanA.valLong}", java.lang.String.class);
        assertEquals("5", ve.getValue(context));
    }

    @Test
    public void testBug50449a() throws Exception {
        MethodExpression me1 = factory.createMethodExpression(context,
                "${beanB.sayHello()}", null, null);
        String actual = (String) me1.invoke(context, null);
        assertEquals("Hello from B", actual);
    }

    @Test
    public void testBug50449b() throws Exception {
        MethodExpression me1 = factory.createMethodExpression(context,
                "${beanB.sayHello('Tomcat')}", null, null);
        String actual = (String) me1.invoke(context, null);
        assertEquals("Hello Tomcat from B", actual);
    }

    @Test
    public void testBug50790a() throws Exception {
        ValueExpression ve = factory.createValueExpression(context,
                "#{beanAA.name.contains(beanA.name)}", java.lang.Boolean.class);
        Boolean actual = (Boolean) ve.getValue(context);
        assertEquals(Boolean.TRUE, actual);
    }

    @Test
    public void testBug50790b() throws Exception {
        ValueExpression ve = factory.createValueExpression(context,
                "#{beanA.name.contains(beanAA.name)}", java.lang.Boolean.class);
        Boolean actual = (Boolean) ve.getValue(context);
        assertEquals(Boolean.FALSE, actual);
    }

    @Test
    public void testBug52445a() {
        MethodExpression me = factory.createMethodExpression(context,
                "${beanA.setBean(beanBB)}", null ,
                new Class<?>[] { TesterBeanB.class });
        me.invoke(context, null);

        MethodExpression me1 = factory.createMethodExpression(context,
                "${beanA.bean.sayHello()}", null, null);
        String actual = (String) me1.invoke(context, null);
        assertEquals("Hello from BB", actual);
    }

    @Test
    public void testBug52970() {
        MethodExpression me = factory.createMethodExpression(context,
                "${beanEnum.submit('APPLE')}", null ,
                new Class<?>[] { TesterBeanEnum.class });
        me.invoke(context, null);

        ValueExpression ve = factory.createValueExpression(context,
                "#{beanEnum.lastSubmitted}", TesterEnum.class);
        TesterEnum actual = (TesterEnum) ve.getValue(context);
        assertEquals(TesterEnum.APPLE, actual);

    }

    @Test
    public void testBug53792a() {
        MethodExpression me = factory.createMethodExpression(context,
                "${beanA.setBean(beanB)}", null ,
                new Class<?>[] { TesterBeanB.class });
        me.invoke(context, null);
        me = factory.createMethodExpression(context,
                "${beanB.setName('" + BUG53792 + "')}", null ,
                new Class<?>[] { TesterBeanB.class });
        me.invoke(context, null);

        ValueExpression ve = factory.createValueExpression(context,
                "#{beanA.getBean().name}", java.lang.String.class);
        String actual = (String) ve.getValue(context);
        assertEquals(BUG53792, actual);
    }

    @Test
    public void testBug53792b() {
        MethodExpression me = factory.createMethodExpression(context,
                "${beanA.setBean(beanB)}", null ,
                new Class<?>[] { TesterBeanB.class });
        me.invoke(context, null);
        me = factory.createMethodExpression(context,
                "${beanB.setName('" + BUG53792 + "')}", null ,
                new Class<?>[] { TesterBeanB.class });
        me.invoke(context, null);

        ValueExpression ve = factory.createValueExpression(context,
                "#{beanA.getBean().name.length()}", java.lang.Integer.class);
        Integer actual = (Integer) ve.getValue(context);
        assertEquals(Integer.valueOf(BUG53792.length()), actual);
    }


    @Test
    public void testBug53792c() {
        MethodExpression me = factory.createMethodExpression(context,
                "#{beanB.sayHello().length()}", null, new Class<?>[] {});
        Integer result = (Integer) me.invoke(context, null);
        assertEquals(beanB.sayHello().length(), result.intValue());
    }


    @Test
    public void testBug53792d() {
        MethodExpression me = factory.createMethodExpression(context,
                "#{beanB.sayHello().length()}", null, new Class<?>[] {});
        Integer result = (Integer) me.invoke(context, new Object[] { "foo" });
        assertEquals(beanB.sayHello().length(), result.intValue());
    }


    @Test
    public void testBug56797a() {
        MethodExpression me = factory.createMethodExpression(context,
                "${beanAA.echo1('Hello World!')}", null , null);
        Object r = me.invoke(context, null);
        assertEquals("AA1Hello World!", r.toString());
    }


    @Test
    public void testBug56797b() {
        MethodExpression me = factory.createMethodExpression(context,
                "${beanAA.echo2('Hello World!')}", null , null);
        Object r = me.invoke(context, null);
        assertEquals("AA2Hello World!", r.toString());
    }


    @Test(expected=IllegalArgumentException.class)
    public void testBug57855a() {
        MethodExpression me = factory.createMethodExpression(context,
                "${beanAA.echo2}", null , new Class[]{String.class});
        me.invoke(context, new Object[0]);
    }


    @Test(expected=IllegalArgumentException.class)
    public void testBug57855b() {
        MethodExpression me = factory.createMethodExpression(context,
                "${beanAA.echo2}", null , new Class[]{String.class});
        me.invoke(context, null);
    }

    @Test
    public void testBug57855c() {
        MethodExpression me = factory.createMethodExpression(context,
                "${beanB.echo}", null , new Class[]{String.class});
        me.invoke(context, null);
    }


    @Test
    public void testBug57855d() {
        MethodExpression me = factory.createMethodExpression(context,
                "${beanB.echo}", null , new Class[]{String.class});
        Object r = me.invoke(context, new String[] { "aaa" });
        assertEquals("aaa", r.toString());
    }
    @Test
    public void testBug57855e() {
        MethodExpression me = factory.createMethodExpression(context,
                "${beanB.echo}", null , new Class[]{String.class});
        Object r = me.invoke(context, new String[] { "aaa", "bbb" });
        assertEquals("aaa, bbb", r.toString());
    }


    @Test(expected=IllegalArgumentException.class)
    public void testBug60844() {
        MethodExpression me2 = factory.createMethodExpression(context,
                "${beanC.sayHello}", null , new Class[]{ TesterBeanA.class, TesterBeanB.class});
        me2.invoke(context, new Object[] { new Object() });
    }
}
