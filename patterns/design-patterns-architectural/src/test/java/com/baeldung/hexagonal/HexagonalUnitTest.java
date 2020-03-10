package com.baeldung.hexagonal;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;

import com.baeldung.hexagonal.adapter.RegularDiscountCalculator;
import com.baeldung.hexagonal.adapter.SalesDiscountCalculator;
import com.baeldung.hexagonal.application.Application;
import com.baeldung.hexagonal.port.PriceCalculator;

public class HexagonalUnitTest {

    private static PriceCalculator regularService = new Application(new RegularDiscountCalculator());
    private static PriceCalculator salesService = new Application(new SalesDiscountCalculator());;

    @Test
    public void givenRegularDiscount_andPrice1_thenCorrectResult() {
        assertThat(regularService.calculateFinalPrice(39.95F) == 37.9525F + 3.99F);
    }

    @Test
    public void givenRegularDiscount_andPrice2_thenCorrectResult() {
        assertThat(regularService.calculateFinalPrice(99.99F) == 94.9905F);
    }

    @Test
    public void givenSalesDiscount_andPrice1_thenCorrectResult() {
        assertThat(salesService.calculateFinalPrice(39.95F) == 29.9625F + 3.99F);
    }

    @Test
    public void givenSalesDiscount_andPrice2_thenCorrectResult() {
        assertThat(salesService.calculateFinalPrice(99.99F) == 74.9925F);
    }

}
