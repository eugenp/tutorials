package com.baeldung.java21;

import org.junit.Assert;
import org.junit.Test;

public class StringTemplateUnitTest {

    @Test
    public void whenNoSwitchPattern_thenReturnSavingsAccountBalance() {
        StringTemplates stringTemplates = new StringTemplates();
        Assert.assertEquals("Welcome to Baeldung", stringTemplates.getStringTemplate());
    }
}
