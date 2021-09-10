package com.example.hexagonalarchitecture.kitchenassistant.application.port.in.dtos;

import com.example.hexagonalarchitecture.utility.SelfValidating;
import lombok.Value;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Value
public class StockRequest extends SelfValidating<StockRequest> {

    @NotNull Long userId;

    @NotNull List<String> foodItems;

    String additionalNote;

    BigDecimal cost;

    public StockRequest(
            Long userId,
            List<String> foodItems,
            String additionalNote) {
        this.userId = userId;
        this.foodItems = foodItems;
        this.additionalNote = additionalNote;
        this.cost = getCost(foodItems);
        this.validateSelf();
    }

    private BigDecimal getCost(List<String> foodItems) {
        return BigDecimal.valueOf(foodItems.size() * 50L);
    }
}
