package com.baeldung.minmaxbygroup;

import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;

public class OrderProcessor {

  public Map<OrderItemCategory, Pair<Double, Double>> groupByCategoryWithMinMax(
      List<OrderItem> orderItems) {
    Map<OrderItemCategory, DoubleSummaryStatistics> categoryStatistics = orderItems.stream()
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
