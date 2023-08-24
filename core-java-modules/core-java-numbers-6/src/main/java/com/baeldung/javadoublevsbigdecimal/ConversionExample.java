package com.baeldung.javadoublevsbigdecimal;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ConversionExample {
    public static BigDecimal[] performConversions() {
        double doubleValue = 123.456;
        BigDecimal bigDecimalValue = BigDecimal.valueOf(doubleValue);
        BigDecimal expected = new BigDecimal("123.456").setScale(3, RoundingMode.HALF_UP);

        BigDecimal bd1 = new BigDecimal("124567890.0987654321");
        BigDecimal bd2 = new BigDecimal("987654321.123456789");

        BigDecimal bigDecimalValue2 = bd2.setScale(9, RoundingMode.HALF_UP);  // Corrected variable name here
        double doubleValue2 = bd1.doubleValue();
        double expected2 = bd1.setScale(9, RoundingMode.HALF_UP).doubleValue();

        return new BigDecimal[]{bigDecimalValue, expected, bigDecimalValue2, new BigDecimal(doubleValue2), new BigDecimal(expected2)};
    }
}
