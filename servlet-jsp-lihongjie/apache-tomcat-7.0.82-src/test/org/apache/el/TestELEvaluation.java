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

import java.io.File;
import java.util.Date;

import javax.el.ELException;
import javax.el.ValueExpression;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.Test;

import org.apache.el.lang.ELSupport;
import org.apache.jasper.el.ELContextImpl;

/**
 * Tests the EL engine directly. Similar tests may be found in
 * {@link org.apache.jasper.compiler.TestAttributeParser} and
 * {@link TestELInJsp}.
 */
public class TestELEvaluation {

    /**
     * Test use of spaces in ternary expressions. This was primarily an EL
     * parser bug.
     */
    @Test
    public void testBug42565() {
        assertEquals("false", evaluateExpression("${false?true:false}"));
        assertEquals("false", evaluateExpression("${false?true: false}"));
        assertEquals("false", evaluateExpression("${false?true :false}"));
        assertEquals("false", evaluateExpression("${false?true : false}"));
        assertEquals("false", evaluateExpression("${false? true:false}"));
        assertEquals("false", evaluateExpression("${false? true: false}"));
        assertEquals("false", evaluateExpression("${false? true :false}"));
        assertEquals("false", evaluateExpression("${false? true : false}"));
        assertEquals("false", evaluateExpression("${false ?true:false}"));
        assertEquals("false", evaluateExpression("${false ?true: false}"));
        assertEquals("false", evaluateExpression("${false ?true :false}"));
        assertEquals("false", evaluateExpression("${false ?true : false}"));
        assertEquals("false", evaluateExpression("${false ? true:false}"));
        assertEquals("false", evaluateExpression("${false ? true: false}"));
        assertEquals("false", evaluateExpression("${false ? true :false}"));
        assertEquals("false", evaluateExpression("${false ? true : false}"));
    }


    /**
     * Test use nested ternary expressions. This was primarily an EL parser bug.
     */
    @Test
    public void testBug44994() {
        assertEquals("none", evaluateExpression(
                "${0 lt 0 ? 1 lt 0 ? 'many': 'one': 'none'}"));
        assertEquals("one", evaluateExpression(
                "${0 lt 1 ? 1 lt 1 ? 'many': 'one': 'none'}"));
        assertEquals("many", evaluateExpression(
                "${0 lt 2 ? 1 lt 2 ? 'many': 'one': 'none'}"));
    }

    @Test
    public void testParserBug45511() {
        // Test cases provided by OP
        assertEquals("true", evaluateExpression("${empty ('')}"));
        assertEquals("true", evaluateExpression("${empty('')}"));
        assertEquals("false", evaluateExpression("${(true) and (false)}"));
        assertEquals("false", evaluateExpression("${(true)and(false)}"));
    }

    @Test
    public void testBug48112() {
        // bug 48112
        assertEquals("{world}", evaluateExpression("${fn:trim('{world}')}"));
    }

    @Test
    public void testParserLiteralExpression() {
        // Inspired by work on bug 45451, comments from kkolinko on the dev
        // list and looking at the spec to find some edge cases

        // '\' is only an escape character inside a StringLiteral
        assertEquals("\\\\", evaluateExpression("\\\\"));

        /*
         * LiteralExpressions can only contain ${ or #{ if escaped with \
         * \ is not an escape character in any other circumstances including \\
         */
        assertEquals("\\", evaluateExpression("\\"));
        assertEquals("$", evaluateExpression("$"));
        assertEquals("#", evaluateExpression("#"));
        assertEquals("\\$", evaluateExpression("\\$"));
        assertEquals("\\#", evaluateExpression("\\#"));
        assertEquals("\\\\$", evaluateExpression("\\\\$"));
        assertEquals("\\\\#", evaluateExpression("\\\\#"));
        assertEquals("${", evaluateExpression("\\${"));
        assertEquals("#{", evaluateExpression("\\#{"));
        assertEquals("\\${", evaluateExpression("\\\\${"));
        assertEquals("\\#{", evaluateExpression("\\\\#{"));

        // '\' is only an escape for '${' and '#{'.
        assertEquals("\\$", evaluateExpression("\\$"));
        assertEquals("${", evaluateExpression("\\${"));
        assertEquals("\\$a", evaluateExpression("\\$a"));
        assertEquals("\\a", evaluateExpression("\\a"));
        assertEquals("\\\\", evaluateExpression("\\\\"));
    }

    @Test
    public void testParserStringLiteral() {
        // Inspired by work on bug 45451, comments from kkolinko on the dev
        // list and looking at the spec to find some edge cases

        // The only characters that can be escaped inside a String literal
        // are \ " and '. # and $ are not escaped inside a String literal.
        assertEquals("\\", evaluateExpression("${'\\\\'}"));
        assertEquals("\\", evaluateExpression("${\"\\\\\"}"));
        assertEquals("\\\"'$#", evaluateExpression("${'\\\\\\\"\\'$#'}"));
        assertEquals("\\\"'$#", evaluateExpression("${\"\\\\\\\"\\'$#\"}"));

        // Trying to quote # or $ should throw an error
        Exception e = null;
        try {
            evaluateExpression("${'\\$'}");
        } catch (ELException el) {
            e = el;
        }
        assertNotNull(e);

        assertEquals("\\$", evaluateExpression("${'\\\\$'}"));
        assertEquals("\\\\$", evaluateExpression("${'\\\\\\\\$'}"));


        // Can use ''' inside '"' when quoting with '"' and vice versa without
        // escaping
        assertEquals("\\\"", evaluateExpression("${'\\\\\"'}"));
        assertEquals("\"\\", evaluateExpression("${'\"\\\\'}"));
        assertEquals("\\'", evaluateExpression("${'\\\\\\''}"));
        assertEquals("'\\", evaluateExpression("${'\\'\\\\'}"));
        assertEquals("\\'", evaluateExpression("${\"\\\\'\"}"));
        assertEquals("'\\", evaluateExpression("${\"'\\\\\"}"));
        assertEquals("\\\"", evaluateExpression("${\"\\\\\\\"\"}"));
        assertEquals("\"\\", evaluateExpression("${\"\\\"\\\\\"}"));
    }

    @Test
    public void testMultipleEscaping() throws Exception {
        assertEquals("''", evaluateExpression("${\"\'\'\"}"));
    }

    private void compareBoth(String msg, int expected, Object o1, Object o2){
        int i1 = ELSupport.compare(o1, o2);
        int i2 = ELSupport.compare(o2, o1);
        assertEquals(msg,expected, i1);
        assertEquals(msg,expected, -i2);
    }

    @Test
    public void testElSupportCompare(){
        compareBoth("Nulls should compare equal", 0, null, null);
        compareBoth("Null should compare equal to \"\"", 0, "", null);
        compareBoth("Null should be less than File()",-1, null, new File(""));
        compareBoth("Null should be less than Date()",-1, null, new Date());
        compareBoth("Date(0) should be less than Date(1)",-1, new Date(0), new Date(1));
        try {
            compareBoth("Should not compare",0, new Date(), new File(""));
            fail("Expecting ClassCastException");
        } catch (ClassCastException expected) {
            // Expected
        }
        assertTrue(null == null);
    }

    /**
     * Test mixing ${...} and #{...} in the same expression.
     */
    @Test
    public void testMixedTypes() {
        // Mixing types should throw an error
        Exception e = null;
        try {
            evaluateExpression("${1+1}#{1+1}");
        } catch (ELException el) {
            e = el;
        }
        assertNotNull(e);
    }

    @Test
    public void testEscape01() {
        Assert.assertEquals("$${", evaluateExpression("$\\${"));
    }

    @Test
    public void testBug49081a() {
        Assert.assertEquals("$2", evaluateExpression("$${1+1}"));
    }

    @Test
    public void testBug49081b() {
        Assert.assertEquals("#2", evaluateExpression("##{1+1}"));
    }

    @Test
    public void testBug49081c() {
        Assert.assertEquals("#2", evaluateExpression("#${1+1}"));
    }

    @Test
    public void testBug49081d() {
        Assert.assertEquals("$2", evaluateExpression("$#{1+1}"));
    }

    @Test
    public void testBug60431a() {
        Assert.assertEquals("OK", evaluateExpression("${fn:concat('O','K')}"));
    }

    @Test
    public void testBug60431b() {
        Assert.assertEquals("OK", evaluateExpression("${fn:concat(fn:toArray('O','K'))}"));
    }

    @Test
    public void testBug60431c() {
        Assert.assertEquals("", evaluateExpression("${fn:concat()}"));
    }

    @Test
    public void testBug60431d() {
        Assert.assertEquals("OK", evaluateExpression("${fn:concat2('OK')}"));
    }

    @Test
    public void testBug60431e() {
        Assert.assertEquals("RUOK", evaluateExpression("${fn:concat2('RU', fn:toArray('O','K'))}"));
    }

    // ************************************************************************

    private String evaluateExpression(String expression) {
        ELContextImpl ctx = new ELContextImpl();
        ctx.setFunctionMapper(new TesterFunctions.FMapper());
        ExpressionFactoryImpl exprFactory = new ExpressionFactoryImpl();
        ValueExpression ve = exprFactory.createValueExpression(ctx, expression,
                String.class);
        return (String) ve.getValue(ctx);
    }
}
