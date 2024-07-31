package com.baeldung.dddcontexts.infrastructure.db;

import com.baeldung.dddcontexts.ordercontext.model.CustomerOrder;
import com.baeldung.dddcontexts.ordercontext.repository.CustomerOrderRepository;
import com.baeldung.dddcontexts.shippingcontext.model.PackageItem;
import com.baeldung.dddcontexts.shippingcontext.model.ShippableOrder;
import com.baeldung.dddcontexts.shippingcontext.repository.ShippingOrderRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class InMemoryOrderStore implements CustomerOrderRepository, ShippingOrderRepository {
    private Map<Integer, PersistenceOrder> ordersDb = new HashMap<>();
    private volatile static InMemoryOrderStore instance = new InMemoryOrderStore();

    @Override
    public void saveCustomerOrder(CustomerOrder order) {
        this.ordersDb.put(order.getOrderId(), new PersistenceOrder(order.getOrderId(),
          order.getPaymentMethod(),
          order.getAddress(),
          order
            .getOrderItems()
            .stream()
            .map(orderItem ->
              new PersistenceOrder.OrderItem(orderItem.getProductId(),
                orderItem.getQuantity(),
                orderItem.getUnitWeight(),
                orderItem.getUnitPrice()))
            .collect(Collectors.toList())
        ));
    }

    @Override
    public Optional<ShippableOrder> findShippableOrder(int orderId) {
        if (!this.ordersDb.containsKey(orderId)) return Optional.empty();
        PersistenceOrder orderRecord = this.ordersDb.get(orderId);
        return Optional.of(
          new ShippableOrder(orderRecord.orderId, orderRecord.orderItems
            .stream().map(orderItem -> new PackageItem(orderItem.productId,
              orderItem.itemWeight,
              orderItem.quantity * orderItem.unitPrice)
            ).collect(Collectors.toList())));
    }

    public static InMemoryOrderStore provider() {
        return instance;
    }

    public static class PersistenceOrder {
        public int orderId;
        public String paymentMethod;
        public String address;
        public List<OrderItem> orderItems;

        public PersistenceOrder(int orderId, String paymentMethod, String address, List<OrderItem> orderItems) {
            this.orderId = orderId;
            this.paymentMethod = paymentMethod;
            this.address = address;
            this.orderItems = orderItems;
        }

        public static class OrderItem {
            public int productId;
            public float unitPrice;
            public float itemWeight;
            public int quantity;

            public OrderItem(int productId, int quantity, float unitWeight, float unitPrice) {
                this.itemWeight = unitWeight;
                this.quantity = quantity;
                this.unitPrice = unitPrice;
                this.productId = productId;
            }
        }
    }
}
