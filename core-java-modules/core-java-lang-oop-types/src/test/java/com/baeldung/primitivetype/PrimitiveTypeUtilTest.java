package com.baeldung.primitivetype;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class PrimitiveTypeUtilTest {

    private PrimitiveTypeUtil primitiveTypeUtil;
    private boolean booleanVal = false;
    private Long longWrapper = 1L;
    private String nonPrimitiveVal = "Test";

    @Before
    public void setup() {
        primitiveTypeUtil = new PrimitiveTypeUtil();
    }

    @Test
    public void givenObjectWhenCheckWithGuavaShouldValidate() {
        assertTrue(primitiveTypeUtil.isPrimitiveTypeByGuava(booleanVal));
        assertTrue(primitiveTypeUtil.isPrimitiveTypeByGuava(longWrapper));
        assertFalse(primitiveTypeUtil.isPrimitiveTypeByGuava(nonPrimitiveVal));
    }

    @Test
    public void givenObjectWhenCheckWithCommansLangShouldValidate() {
        assertTrue(primitiveTypeUtil.isPrimitiveTypeByCommansLang(booleanVal));
        assertTrue(primitiveTypeUtil.isPrimitiveTypeByCommansLang(longWrapper));
        assertFalse(primitiveTypeUtil.isPrimitiveTypeByCommansLang(nonPrimitiveVal));
    }

    @Test
    public void givenPrimitiveOrWrapperWhenCheckWithCustomMethodShouldReturnTrue() {
        assertTrue(primitiveTypeUtil.isPrimitiveType(booleanVal));
        assertTrue(primitiveTypeUtil.isPrimitiveType(longWrapper));
        assertFalse(primitiveTypeUtil.isPrimitiveType(nonPrimitiveVal));
    }
}
