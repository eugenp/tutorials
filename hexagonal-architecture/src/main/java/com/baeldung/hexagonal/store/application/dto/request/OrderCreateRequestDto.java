package com.baeldung.hexagonal.store.application.dto.request;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OrderCreateRequestDto {
    List<OrderProductDescriptor> productIds;

    public Map<Long, Integer> getProductQuantityMap() {
        return this.productIds
                .stream()
                .collect(Collectors.toMap(OrderProductDescriptor::getProductId, OrderProductDescriptor::getQuantity));
    }

    public static class OrderProductDescriptor {
        private long productId;
        private int quantity;

        public int getQuantity() {
            return quantity;
        }

        public long getProductId() {
            return productId;
        }
    }
}
