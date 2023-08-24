package com.baeldung.javadoublevsbigdecimal;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BigDecimalExample {
    public static BigDecimal[] performBigDecimalOperations() {
        BigDecimal bd1 = new BigDecimal("124567890.0987654321");
        BigDecimal bd2 = new BigDecimal("987654321.123456789");

        BigDecimal resultAdd = bd1.add(bd2);
        BigDecimal resultMultiply = bd1.multiply(bd2);
        BigDecimal resultSubtract = bd1.subtract(bd2);
        BigDecimal resultDivide = bd1.divide(bd2, 2, RoundingMode.HALF_UP);

        return new BigDecimal[]{resultAdd, resultMultiply, resultSubtract, resultDivide};
    }
}
