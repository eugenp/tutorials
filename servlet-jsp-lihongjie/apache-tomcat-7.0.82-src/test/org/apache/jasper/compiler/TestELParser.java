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
package org.apache.jasper.compiler;

import javax.el.ELContext;
import javax.el.ELException;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import org.junit.Assert;
import org.junit.Test;

import org.apache.jasper.JasperException;
import org.apache.jasper.compiler.ELNode.Nodes;
import org.apache.jasper.compiler.ELParser.TextBuilder;
import org.apache.jasper.el.ELContextImpl;

/**
 * You will need to keep your wits about you when working with this class. Keep
 * in mind the following:
 * <ul>
 * <li>If in doubt, read the EL and JSP specifications. Twice.</li>
 * <li>The escaping rules are complex and subtle. The explanation below (as well
 *     as the tests and the implementation) may have missed an edge case despite
 *     trying hard not to.
 * <li>The strings passed to {@link #doTestParser(String,String)} are Java
 *     escaped in the source code and will be unescaped before being used.</li>
 * <li>LiteralExpressions always occur outside of "${...}" and "#{...}". Literal
 *     expressions escape '$' and '#' with '\\'</li>
 * <li>LiteralStrings always occur inside "${...}" or "#{...}". Literal strings
 *     escape '\'', '\"' and '\\' with '\\'. Escaping '\"' is optional if the
 *     literal string is delimited by '\''. Escaping '\'' is optional if the
 *     literal string is delimited by '\"'.</li>
 * </ul>
 */
public class TestELParser {

    @Test
    public void testText() throws JasperException {
        doTestParser("foo", "foo");
    }


    @Test
    public void testLiteral() throws JasperException {
        doTestParser("${'foo'}", "foo");
    }


    @Test
    public void testVariable() throws JasperException {
        doTestParser("${test}", null);
    }


    @Test
    public void testFunction01() throws JasperException {
        doTestParser("${do(x)}", null);
    }


    @Test
    public void testFunction02() throws JasperException {
        doTestParser("${do:it(x)}", null);
    }


    @Test
    public void testFunction03() throws JasperException {
        doTestParser("${do:it(x,y)}", null);
    }


    @Test
    public void testFunction04() throws JasperException {
        doTestParser("${do:it(x,y,z)}", null);
    }


    @Test
    public void testFunction05() throws JasperException {
        doTestParser("${do:it(x, '\\\\y',z)}", null);
    }


    @Test
    public void testCompound01() throws JasperException {
        doTestParser("1${'foo'}1", "1foo1");
    }


    @Test
    public void testCompound02() throws JasperException {
        doTestParser("1${test}1", null);
    }


    @Test
    public void testCompound03() throws JasperException {
        doTestParser("${foo}${bar}", null);
    }


    @Test
    public void testTernary01() throws JasperException {
        doTestParser("${true?true:false}", "true");
    }


    @Test
    public void testTernary02() throws JasperException {
        doTestParser("${a==1?true:false}", null);
    }


    @Test
    public void testTernary03() throws JasperException {
        doTestParser("${a eq1?true:false}", null);
    }


    @Test
    public void testTernary04() throws JasperException {
        doTestParser(" ${ a eq 1 ? true : false } ", null);
    }


    @Test
    public void testTernary05() throws JasperException {
        // Note this is invalid EL
        doTestParser("${aeq1?true:false}", null);
    }


    @Test
    public void testTernary06() throws JasperException {
        doTestParser("${do:it(a eq1?true:false,y)}", null);
    }


    @Test
    public void testTernary07() throws JasperException {
        doTestParser(" ${ do:it( a eq 1 ? true : false, y ) } ", null);
    }

    
    @Test
    public void testTernary08() throws JasperException {
        doTestParser(" ${ do:it ( a eq 1 ? true : false, y ) } ", null);
    }


    @Test
    public void testTernary09() throws JasperException {
        doTestParser(" ${ do : it ( a eq 1 ? true : false, y ) } ", null);
    }


    @Test
    public void testTernary10() throws JasperException {
        doTestParser(" ${!empty my:link(foo)} ", null);
    }


    @Test
    public void testTernary11() throws JasperException {
        doTestParser("${true?'true':'false'}", "true");
    }


    @Test
    public void testTernary12() throws JasperException {
        doTestParser("${true?'tr\"ue':'false'}", "tr\"ue");
    }


    @Test
    public void testTernary13() throws JasperException {
        doTestParser("${true?'tr\\'ue':'false'}", "tr'ue");
    }


    @Test
    public void testTernaryBug56031() throws JasperException {
        doTestParser("${my:link(!empty registration ? registration : '/test/registration')}", null);
    }


    @Test
    public void testQuotes01() throws JasperException {
        doTestParser("'", "'");
    }


    @Test
    public void testQuotes02() throws JasperException {
        doTestParser("'${foo}'", null);
    }


    @Test
    public void testQuotes03() throws JasperException {
        doTestParser("'${'foo'}'", "'foo'");
    }


    @Test
    public void testEscape01() throws JasperException {
        doTestParser("${'\\\\'}", "\\");
    }


    @Test
    public void testEscape02() throws JasperException {
        doTestParser("\\\\x${'\\\\'}", "\\\\x\\");
    }


    @Test
    public void testEscape03() throws JasperException {
        doTestParser("\\\\", "\\\\");
    }


    @Test
    public void testEscape04() throws JasperException {
        // When parsed as EL in JSP the escaping of $ as \$ is optional
        doTestParser("\\$", "\\$", "$");
    }


    @Test
    public void testEscape05() throws JasperException {
        // When parsed as EL in JSP the escaping of # as \# is optional
        doTestParser("\\#", "\\#", "#");
    }


    @Test
    public void testEscape07() throws JasperException {
        doTestParser("${'\\\\$'}", "\\$");
    }


    @Test
    public void testEscape08() throws JasperException {
        doTestParser("${'\\\\#'}", "\\#");
    }


    @Test
    public void testEscape09() throws JasperException {
        doTestParser("\\${", "${");
    }


    @Test
    public void testEscape10() throws JasperException {
        doTestParser("\\#{", "#{");
    }


    @Test
    public void testEscape11() throws JasperException {
        // Bug 56612
        doTestParser("${'\\'\\''}", "''");
    }


    private void doTestParser(String input, String expected) throws JasperException {
        doTestParser(input, expected, input);
    }

    private void doTestParser(String input, String expectedResult, String expectedBuilderOutput) throws JasperException {

        ELException elException = null;
        String elResult = null;

        // Don't try and evaluate expressions that depend on variables or functions
        if (expectedResult != null) {
            try {
                ExpressionFactory factory = ExpressionFactory.newInstance();
                ELContext context = new ELContextImpl();
                ValueExpression ve = factory.createValueExpression(context, input, String.class);
                elResult = ve.getValue(context).toString();
                Assert.assertEquals(expectedResult, elResult);
            } catch (ELException ele) {
                elException = ele;
            }
        }

        Nodes nodes = null;
        try {
            nodes = ELParser.parse(input, false);
            Assert.assertNull(elException);
        } catch (IllegalArgumentException iae) {
            Assert.assertNotNull(elResult, elException);
            // Not strictly true but enables us to report both
            iae.initCause(elException);
            throw iae;
        }

        TextBuilder textBuilder = new TextBuilder(false);

        nodes.visit(textBuilder);

        Assert.assertEquals(expectedBuilderOutput, textBuilder.getText());
    }
}
