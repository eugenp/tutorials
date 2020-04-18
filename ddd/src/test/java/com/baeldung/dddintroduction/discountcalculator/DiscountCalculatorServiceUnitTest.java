package com.baeldung.dddintroduction.discountcalculator;

import com.baeldung.dddintroduction.discountcalculator.impl.DiscountCalculatorService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

//Example of Secondary Adapter
@RunWith(MockitoJUnitRunner.class)
public class DiscountCalculatorServiceUnitTest {

    @InjectMocks
    private DiscountCalculatorService discountCalculatorService;

    @Test
    public void calculateDiscount_shouldReturnPriceAs75Point5_whenPriceIs100AndDiscountIs24Point5() {
        BigDecimal discount = discountCalculatorService.calculateDiscount(new BigDecimal("100"), new BigDecimal("24.5"));
        assertEquals(new BigDecimal("75.5"), discount);
    }
}