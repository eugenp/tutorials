package com.baeldung.streams.minmaxbygroup;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class OrderProcessorUnitTest {
  @Test
  void whenOrderItemsAreGrouped_thenGetsMinMaxPerGroup() {
    List<OrderItem> items =
        Arrays.asList(
            new OrderItem(1L, OrderCategory.ELECTRONICS, 1299.99),
            new OrderItem(2L, OrderCategory.ELECTRONICS, 1199.99),
            new OrderItem(3L, OrderCategory.ELECTRONICS, 2199.99),
            new OrderItem(4L, OrderCategory.FURNITURE, 220.00),
            new OrderItem(4L, OrderCategory.FURNITURE, 200.20),
            new OrderItem(5L, OrderCategory.FURNITURE, 215.00),
            new OrderItem(6L, OrderCategory.CLOTHING, 50.75),
            new OrderItem(7L, OrderCategory.CLOTHING, 75.00),
            new OrderItem(8L, OrderCategory.CLOTHING, 75.00));

    OrderProcessor orderProcessor = new OrderProcessor();
    final Map<OrderCategory, Pair<Double, Double>> orderCategoryPairMap =
        orderProcessor.groupByCategoryWithMinMax(items);
    Assertions.assertEquals(
        orderCategoryPairMap.get(OrderCategory.ELECTRONICS), Pair.of(1199.99, 2199.99));
    Assertions.assertEquals(
        orderCategoryPairMap.get(OrderCategory.FURNITURE), Pair.of(200.20, 220.00));
    Assertions.assertEquals(
        orderCategoryPairMap.get(OrderCategory.CLOTHING), Pair.of(50.75, 75.00));
  }
}
