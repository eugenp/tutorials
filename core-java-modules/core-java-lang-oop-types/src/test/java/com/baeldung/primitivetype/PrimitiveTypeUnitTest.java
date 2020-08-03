package com.baeldung.primitivetype;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.apache.commons.lang3.ClassUtils;
import org.junit.Before;
import org.junit.Test;

import com.google.common.primitives.Primitives;

public class PrimitiveTypeUnitTest {

    private PrimitiveTypeUtil primitiveTypeUtil;

    @Before
    public void setup() {
        primitiveTypeUtil = new PrimitiveTypeUtil();
    }

    @Test
    public void givenAClass_whenCheckWithPrimitiveTypeUtil_thenShouldValidate() {
        assertTrue(primitiveTypeUtil.isPrimitiveType(Boolean.class));
        assertFalse(primitiveTypeUtil.isPrimitiveType(String.class));
    }

    @Test
    public void givenAClass_whenCheckWithCommonsLang_thenShouldValidate() {
        assertTrue(ClassUtils.isPrimitiveOrWrapper(Boolean.class));
        assertTrue(ClassUtils.isPrimitiveOrWrapper(boolean.class));
        assertFalse(ClassUtils.isPrimitiveOrWrapper(String.class));
    }

    @Test
    public void givenAClass_whenCheckWithGuava_thenShouldValidate() {
        assertTrue(Primitives.isWrapperType(Boolean.class));
        assertFalse(Primitives.isWrapperType(String.class));
        assertFalse(Primitives.isWrapperType(boolean.class));
    }
}
