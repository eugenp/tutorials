package com.baeldung.streams.minmaxbygroup;

import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.lang3.tuple.Pair;

/** The type Order processor. */
public class OrderProcessor {
  /**
   * Group by category with min max per group.
   *
   * @param orderItems the order items
   * @return the grouped items
   */
  public Map<OrderCategory, Pair<Double, Double>> groupByCategoryWithMinMax(
      List<OrderItem> orderItems) {
    Map<OrderCategory, DoubleSummaryStatistics> categoryStatistics =
        orderItems.stream()
            .collect(
                Collectors.groupingBy(
                    OrderItem::getCategory, Collectors.summarizingDouble(OrderItem::getPrice)));

    return categoryStatistics.entrySet().stream()
        .collect(
            Collectors.toMap(
                Map.Entry::getKey,
                entry -> Pair.of(entry.getValue().getMin(), entry.getValue().getMax())));
  }
}
