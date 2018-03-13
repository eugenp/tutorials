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
package org.apache.el.lang;

import java.beans.PropertyEditorManager;
import java.math.BigDecimal;
import java.math.BigInteger;

import javax.el.ELException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.junit.Test;

public class TestELSupport {
    @Test
    public void testEquals() {
        assertTrue(ELSupport.equals("01", Long.valueOf(1)));
    }

    @Test
    public void testBigDecimal() {
        testIsSame(new BigDecimal(
                "0.123456789012345678901234567890123456789012345678901234567890123456789"));
    }

    @Test
    public void testBigInteger() {
        testIsSame(new BigInteger(
                "1234567890123456789012345678901234567890123456789012345678901234567890"));
    }

    @Test
    public void testLong() {
        testIsSame(Long.valueOf(0x0102030405060708L));
    }

    @Test
    public void testInteger() {
        testIsSame(Integer.valueOf(0x01020304));
    }

    @Test
    public void testShort() {
        testIsSame(Short.valueOf((short) 0x0102));
    }

    @Test
    public void testByte() {
        testIsSame(Byte.valueOf((byte) 0xEF));
    }

    @Test
    public void testDouble() {
        testIsSame(Double.valueOf(0.123456789012345678901234));
    }

    @Test
    public void testFloat() {
        testIsSame(Float.valueOf(0.123456F));
    }

    @Test
    public void testCoerceIntegerToNumber() {
        Integer input = Integer.valueOf(4390241);
        Object output = ELSupport.coerceToType(input, Number.class);
        assertEquals(input, output);
    }

    @Test
    public void testCoerceNullToNumber() {
        Object output = ELSupport.coerceToType(null, Number.class);
        assertEquals(Long.valueOf(0), output);
    }

    @Test
    public void testCoerceEnumAToEnumA() {
        Object output = null;
        try {
            output = ELSupport.coerceToEnum(TestEnumA.VALA1, TestEnumA.class);
        } finally {
            assertEquals(TestEnumA.VALA1, output);
        }
    }

    @Test
    public void testCoerceEnumAToEnumB() {
        Object output = null;
        try {
            output = ELSupport.coerceToEnum(TestEnumA.VALA1, TestEnumB.class);
        } catch (ELException ele) {
            // Ignore
        }
        assertNull(output);
    }

    @Test
    public void testCoerceEnumAToEnumC() {
        Object output = null;
        try {
            output = ELSupport.coerceToEnum(TestEnumA.VALA1, TestEnumC.class);
        } catch (ELException ele) {
            // Ignore
        }
        assertNull(output);
    }

    @Test
    public void testCoerceToType01() {
        Object result = ELSupport.coerceToType(null, Integer.class);
        Assert.assertEquals(Integer.valueOf(0), result);
    }

    @Test
    public void testCoerceToType02() {
        Object result = ELSupport.coerceToType(null, int.class);
        Assert.assertEquals(Integer.valueOf(0), result);
    }

    @Test
    public void testCoerceToType03() {
        Object result = ELSupport.coerceToType(null, boolean.class);
        Assert.assertEquals(Boolean.valueOf(null), result);
    }

    @Test
    public void testCoerceToType04() {
        Object result = ELSupport.coerceToType(null, String.class);
        Assert.assertEquals("", result);
    }

    @Test
    public void testCoerceToType05() {
        Object result = ELSupport.coerceToType(null, Character.class);
        Assert.assertEquals(Character.valueOf((char) 0), result);
    }

    @Test
    public void testCoerceToType06() {
        Object result = ELSupport.coerceToType("", Character.class);
        Assert.assertEquals(Character.valueOf((char) 0), result);
    }

    @Test
    public void testCoerceToType07() {
        Object result = ELSupport.coerceToType(null, char.class);
        Assert.assertEquals(Character.valueOf((char) 0), result);
    }

    @Test
    public void testCoerceToType08() {
        Object result = ELSupport.coerceToType("", char.class);
        Assert.assertEquals(Character.valueOf((char) 0), result);
    }

    @Test
    public void testCoerceToType09() {
        Object result = ELSupport.coerceToType(null, Boolean.class);
        Assert.assertEquals(Boolean.FALSE, result);
    }

    @Test
    public void testCoerceToType10() {
        Object result = ELSupport.coerceToType("", Boolean.class);
        Assert.assertEquals(Boolean.FALSE, result);
    }

    @Test
    public void testCoerceToType11() {
        Object result = ELSupport.coerceToType(null, boolean.class);
        Assert.assertEquals(Boolean.FALSE, result);
    }

    @Test
    public void testCoerceToType12() {
        Object result = ELSupport.coerceToType("", boolean.class);
        Assert.assertEquals(Boolean.FALSE, result);
    }

    @Test
    public void testCoerceToType13() {
        Object result = ELSupport.coerceToType("", TesterType.class);
        Assert.assertNull(result);
    }

    @Test
    public void testCoerceToType14() {
        PropertyEditorManager.registerEditor(TesterType.class, TesterTypeEditorNoError.class);
        Object result = ELSupport.coerceToType("Foo", TesterType.class);
        Assert.assertTrue(result instanceof TesterType);
        Assert.assertEquals("Foo", ((TesterType) result).getValue());
    }

    @Test(expected=ELException.class)
    public void testCoerceToType15() {
        PropertyEditorManager.registerEditor(TesterType.class, TesterTypeEditorError.class);
        Object result = ELSupport.coerceToType("Foo", TesterType.class);
        Assert.assertTrue(result instanceof TesterType);
        Assert.assertEquals("Foo", ((TesterType) result).getValue());
    }

    @Test
    public void testCoerceToType16() {
        PropertyEditorManager.registerEditor(TesterType.class, TesterTypeEditorError.class);
        Object result = ELSupport.coerceToType("", TesterType.class);
        Assert.assertNull(result);
    }

    @Test
    public void testCoerceToNumber01() {
        Object result = ELSupport.coerceToNumber((Object) null, Integer.class);
        Assert.assertEquals(Integer.valueOf(0), result);
    }

    @Test
    public void testCoerceToNumber02() {
        Object result = ELSupport.coerceToNumber((Object) null, int.class);
        Assert.assertEquals(Integer.valueOf(0), result);
    }

    private static void testIsSame(Object value) {
        assertEquals(value, ELSupport.coerceToNumber(value, value.getClass()));
    }

    private static enum TestEnumA {
        VALA1,
        VALA2
    }
    private static enum TestEnumB {
        VALB1,
        VALB2
    }
    private static enum TestEnumC {
        VALA1,
        VALA2,
        VALB1,
        VALB2
    }
}
