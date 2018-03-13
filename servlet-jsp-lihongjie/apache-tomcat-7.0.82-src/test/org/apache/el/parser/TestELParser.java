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

package org.apache.el.parser;

import javax.el.ELContext;
import javax.el.ELException;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import org.apache.jasper.el.ELContextImpl;

public class TestELParser {

    @Test
    public void testBug49081() {
        // OP's report
        testExpression("#${1+1}", "#2");

        // Variations on a theme
        testExpression("#", "#");
        testExpression("##", "##");
        testExpression("###", "###");
        testExpression("$", "$");
        testExpression("$$", "$$");
        testExpression("$$$", "$$$");
        testExpression("#$", "#$");
        testExpression("#$#", "#$#");
        testExpression("$#", "$#");
        testExpression("$#$", "$#$");

        testExpression("#{1+1}", "2");
        testExpression("##{1+1}", "#2");
        testExpression("###{1+1}", "##2");
        testExpression("${1+1}", "2");
        testExpression("$${1+1}", "$2");
        testExpression("$$${1+1}", "$$2");
        testExpression("#${1+1}", "#2");
        testExpression("#$#{1+1}", "#$2");
        testExpression("$#{1+1}", "$2");
        testExpression("$#${1+1}", "$#2");
    }

    @Test
    public void testJavaKeyWordSuffix() {
        ExpressionFactory factory = ExpressionFactory.newInstance();
        ELContext context = new ELContextImpl();

        TesterBeanA beanA = new TesterBeanA();
        beanA.setInt("five");
        ValueExpression var =
            factory.createValueExpression(beanA, TesterBeanA.class);
        context.getVariableMapper().setVariable("beanA", var);

        // Should fail
        Exception e = null;
        try {
            factory.createValueExpression(context, "${beanA.int}",
                    String.class);
        } catch (ELException ele) {
            e = ele;
        }
        assertNotNull(e);
    }

    @Test
    public void testJavaKeyWordIdentifier() {
        ExpressionFactory factory = ExpressionFactory.newInstance();
        ELContext context = new ELContextImpl();

        TesterBeanA beanA = new TesterBeanA();
        beanA.setInt("five");
        ValueExpression var =
            factory.createValueExpression(beanA, TesterBeanA.class);
        context.getVariableMapper().setVariable("this", var);

        // Should fail
        Exception e = null;
        try {
            factory.createValueExpression(context, "${this}", String.class);
        } catch (ELException ele) {
            e = ele;
        }
        assertNotNull(e);
    }


    @Test
    public void bug56179a() {
        doTestBug56179(0, "test == true");
    }

    @Test
    public void bug56179b() {
        doTestBug56179(1, "test == true");
    }

    @Test
    public void bug56179c() {
        doTestBug56179(2, "test == true");
    }

    @Test
    public void bug56179d() {
        doTestBug56179(3, "test == true");
    }

    @Test
    public void bug56179e() {
        doTestBug56179(4, "test == true");
    }

    @Test
    public void bug56179f() {
        doTestBug56179(5, "test == true");
    }

    @Test
    public void bug56179g() {
        doTestBug56179(0, "(test) == true");
    }

    @Test
    public void bug56179h() {
        doTestBug56179(1, "(test) == true");
    }

    @Test
    public void bug56179i() {
        doTestBug56179(2, "(test) == true");
    }

    @Test
    public void bug56179j() {
        doTestBug56179(3, "(test) == true");
    }

    @Test
    public void bug56179k() {
        doTestBug56179(4, "(test) == true");
    }

    @Test
    public void bug56179l() {
        doTestBug56179(5, "(test) == true");
    }

    @Test
    public void bug56179m() {
        doTestBug56179(5, "((test)) == true");
    }

    @Test
    public void bug56179n() {
        doTestBug56179(5, "(((test))) == true");
    }

    private void doTestBug56179(int parenthesesCount, String innerExpr) {
        ExpressionFactory factory = ExpressionFactory.newInstance();
        ELContext context = new ELContextImpl();

        ValueExpression var =
            factory.createValueExpression(Boolean.TRUE, Boolean.class);
        context.getVariableMapper().setVariable("test", var);

        StringBuilder expr = new StringBuilder();
        expr.append("${");
        for (int i = 0; i < parenthesesCount; i++) {
            expr.append("(");
        }
        expr.append(innerExpr);
        for (int i = 0; i < parenthesesCount; i++) {
            expr.append(")");
        }
        expr.append("}");
        ValueExpression ve = factory.createValueExpression(
                context, expr.toString(), String.class);

        String result = (String) ve.getValue(context);
        assertEquals("true", result);
    }

    @Test
    public void bug56185() {
        ExpressionFactory factory = ExpressionFactory.newInstance();
        ELContext context = new ELContextImpl();

        TesterBeanC beanC = new TesterBeanC();
        ValueExpression var =
            factory.createValueExpression(beanC, TesterBeanC.class);
        context.getVariableMapper().setVariable("myBean", var);

        ValueExpression ve = factory.createValueExpression(context,
            "${(myBean.int1 > 1 and myBean.myBool) or "+
            "((myBean.myBool or myBean.myBool1) and myBean.int1 > 1)}",
            Boolean.class);
        assertEquals(Boolean.FALSE, ve.getValue(context));
        beanC.setInt1(2);
        beanC.setMyBool1(true);
        assertEquals(Boolean.TRUE, ve.getValue(context));
    }

    private void testExpression(String expression, String expected) {
        ExpressionFactory factory = ExpressionFactory.newInstance();
        ELContext context = new ELContextImpl();

        ValueExpression ve = factory.createValueExpression(
                context, expression, String.class);

        String result = (String) ve.getValue(context);
        assertEquals(expected, result);
    }
}
