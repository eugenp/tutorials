package com.baeldung.streams.minmaxbygroup;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;

class OrderProcessorUnitTest {

  @Test
  void whenOrderItemsAreGrouped_thenGetsMinMaxPerGroup() {
    List<OrderItem> items =
        Arrays.asList(
            new OrderItem(1L, OrderItemCategory.ELECTRONICS, 1299.99),
            new OrderItem(2L, OrderItemCategory.ELECTRONICS, 1199.99),
            new OrderItem(3L, OrderItemCategory.ELECTRONICS, 2199.99),
            new OrderItem(4L, OrderItemCategory.FURNITURE, 220.00),
            new OrderItem(4L, OrderItemCategory.FURNITURE, 200.20),
            new OrderItem(5L, OrderItemCategory.FURNITURE, 215.00),
            new OrderItem(6L, OrderItemCategory.CLOTHING, 50.75),
            new OrderItem(7L, OrderItemCategory.CLOTHING, 75.00),
            new OrderItem(8L, OrderItemCategory.CLOTHING, 75.00));

    OrderProcessor orderProcessor = new OrderProcessor();
    final Map<OrderItemCategory, Pair<Double, Double>> orderItemCategoryPairMap =
        orderProcessor.groupByCategoryWithMinMax(items);
    assertEquals(orderItemCategoryPairMap.get(OrderItemCategory.ELECTRONICS), Pair.of(1199.99, 2199.99));
    assertEquals(orderItemCategoryPairMap.get(OrderItemCategory.FURNITURE), Pair.of(200.20, 220.00));
    assertEquals(orderItemCategoryPairMap.get(OrderItemCategory.CLOTHING), Pair.of(50.75, 75.00));
  }
}
