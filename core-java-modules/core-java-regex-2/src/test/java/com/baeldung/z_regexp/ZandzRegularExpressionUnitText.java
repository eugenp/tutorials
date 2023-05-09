package com.baeldung.z_regexp;
import com.baeldung.regex.z_regexp.ZandzRegularExpression;
import org.junit.jupiter.api.Assertions;
import org.testng.annotations.Test;
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
