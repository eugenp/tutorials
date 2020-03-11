package com.baeldung.hexagonal;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;

import com.baeldung.hexagonal.adapter.PrimaryPriceCalculator;
import com.baeldung.hexagonal.adapter.SecondaryRegularDiscountCalculator;
import com.baeldung.hexagonal.adapter.SecondarySalesDiscountCalculator;
import com.baeldung.hexagonal.application.Application;

public class HexagonalUnitTest {

    private static Application regularApplication = new Application(new PrimaryPriceCalculator(new SecondaryRegularDiscountCalculator()));
    private static Application salesApplication = new Application(new PrimaryPriceCalculator(new SecondarySalesDiscountCalculator()));

    @Test
    public void givenRegularDiscount_andPrice1_thenCorrectResult() {
        assertThat(regularApplication.getPrimaryAdapter().calculateFinalPrice(39.95F) == 37.9525F + 3.99F);
    }

    @Test
    public void givenRegularDiscount_andPrice2_thenCorrectResult() {
        assertThat(regularApplication.getPrimaryAdapter().calculateFinalPrice(99.99F) == 94.9905F);
    }

    @Test
    public void givenSalesDiscount_andPrice1_thenCorrectResult() {
        assertThat(salesApplication.getPrimaryAdapter().calculateFinalPrice(39.95F) == 29.9625F + 3.99F);
    }

    @Test
    public void givenSalesDiscount_andPrice2_thenCorrectResult() {
        assertThat(salesApplication.getPrimaryAdapter().calculateFinalPrice(99.99F) == 74.9925F);
    }

}
