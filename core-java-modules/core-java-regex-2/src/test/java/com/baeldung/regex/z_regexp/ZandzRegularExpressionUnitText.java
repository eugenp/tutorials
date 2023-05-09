package com.baeldung.regex.z_regexp;

import org.junit.jupiter.api.Test;

public class ZandzRegularExpressionUnitText {

    @Test
    void testCapitalCase() {
        ZandzRegularExpression expression = new ZandzRegularExpression();
        String INPUT = "Welcome to\n Baeldung.\n";
        String PATTERS = "Baeldung.";
        expression.CapitalCase(INPUT, PATTERS);
    }

    @Test
    void testSmallCase() {
        ZandzRegularExpression expression = new ZandzRegularExpression();
        String INPUT = "Welcome to\n Baeldung.";
        String PATTERS = "Baeldung.";
        expression.SmallCase(INPUT, PATTERS);
    }
}
