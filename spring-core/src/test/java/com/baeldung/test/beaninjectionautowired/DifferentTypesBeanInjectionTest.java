package com.baeldung.test.beaninjectionautowired;

import static org.junit.Assert.*;

import org.junit.Test;

import com.baeldung.beaninjectionautowired.InitBeans;

public class DifferentTypesBeanInjectionTest {
    InitBeans tb = new InitBeans();

    @Test
    public void givenWiredAttibute_whenIsNotNull_thenSuccess() {
        assertNotNull("Injection to Attribute", tb.getAttr());
    }

    @Test
    public void givenWiredConstructorParam_whenIsNotNull_thenSuccess() {
        assertNotNull("Injection to Constructor", tb.getCons());
    }

    @Test
    public void givenWiredSetterMetodParam_whenIsNotNull_thenSuccess() {
        assertNotNull("Injection to Method", tb.getSetter());
    }

    @Test
    public void givenWiredAttibuteAndConstructorParam_whenAreNotIdentical_thenSuccess() {
        assertNotEquals(tb.getAttr().tukTukBean(), tb.getCons().tukTukBean());
    }

    @Test
    public void givenConstructorParamAndSetterMethodParam_whenAreNotIdentical_thenSuccess() {
        assertNotEquals(tb.getCons().tukTukBean(), tb.getSetter().tukTukBean());
    }

    @Test
    public void givenWiredAttibuteAndSetterMethodParam_whenAreNotIdentical_thenSuccess() {
        assertNotEquals(tb.getAttr().tukTukBean(), tb.getSetter().tukTukBean());
    }

    protected void finalize() {
        if (tb != null) {
            tb.close();
        }
    }

}
