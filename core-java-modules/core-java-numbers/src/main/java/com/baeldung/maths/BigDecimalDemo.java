package com.baeldung.maths;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BigDecimalDemo {

    /** Calculate total amount to be paid for an item rounded to cents..
     * @param quantity
     * @param unitPrice
     * @param discountRate
     * @param taxRate
     * @return
     */
    public static BigDecimal calculateTotalAmount(BigDecimal quantity,
        BigDecimal unitPrice, BigDecimal discountRate, BigDecimal taxRate) {
        BigDecimal amount = quantity.multiply(unitPrice);
        BigDecimal discountedAmount = amount.multiply(BigDecimal.ONE.subtract(discountRate));
        BigDecimal total = discountedAmount.multiply(BigDecimal.ONE.add(taxRate));

        // round to 2 decimal places using HALF_EVEN
        return total.setScale(2, RoundingMode.HALF_EVEN);
    }

}
