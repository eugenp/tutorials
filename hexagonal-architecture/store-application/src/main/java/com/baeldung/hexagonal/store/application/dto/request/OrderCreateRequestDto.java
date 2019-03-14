package com.baeldung.hexagonal.store.application.dto.request;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OrderCreateRequestDto {
    List<OrderProductDescriptor> productIds;

    public List<OrderProductDescriptor> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<OrderProductDescriptor> productIds) {
        this.productIds = productIds;
    }

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

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public long getProductId() {
            return productId;
        }

        public void setProductId(long productId) {
            this.productId = productId;
        }
    }
}
