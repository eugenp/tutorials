package com.baeldung.dddhexagonalsimple.domain;

import com.baeldung.dddhexagonalsimple.domain.model.Pizza;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;

@AllArgsConstructor
public class PriceCalculator {

    public BigDecimal calculateRoundedPriceOfPizza(Pizza pizza, Integer diameterInInches) {
        BigDecimal precisePrice = calculatePriceOfPizza(pizza.getPricePerSquareInch(), diameterInInches);

        return roundPriceUpToNearestCent(precisePrice);
    }

    private BigDecimal calculatePriceOfPizza(BigDecimal pricePerSquareInch, Integer diameterInInches) {
        double areaOfPizzaInSquareInches = Math.pow(0.5 * diameterInInches, 2) * Math.PI;

        return new BigDecimal(areaOfPizzaInSquareInches).multiply(pricePerSquareInch);
    }

    private BigDecimal roundPriceUpToNearestCent(BigDecimal precisePrice) {
        return precisePrice.setScale(2, RoundingMode.HALF_UP);
    }
}
