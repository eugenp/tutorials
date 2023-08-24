package com.baeldung.javadoublevsbigdecimal;

import com.baeldung.javadoublevsbigdecimal.BigDecimalExample;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.jupiter.api.Test;

public class BigDecimalExampleUnitTest {

    @Test
    void whenPerformingBigDecimalOperations_thenResultsAreCorrect() {
        BigDecimal[] results = BigDecimalExample.performBigDecimalOperations();

        assertEquals(new BigDecimal("1112222211.222222221"), results[0].setScale(9, RoundingMode.HALF_UP));
        assertEquals(new BigDecimal("123030014929277547.503095577"), results[1].setScale(9, RoundingMode.HALF_UP));
        assertEquals(new BigDecimal("-863086431.0246913569"), results[2].setScale(9, RoundingMode.HALF_UP));
        assertEquals(new BigDecimal("0.13"), results[3].setScale(2, RoundingMode.HALF_UP));
    }
}
