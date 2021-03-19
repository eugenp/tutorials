package com.baeldung.axon.querymodel;

import com.baeldung.axon.coreapi.events.OrderConfirmedEvent;
import com.baeldung.axon.coreapi.events.OrderPlacedEvent;
import com.baeldung.axon.coreapi.events.OrderShippedEvent;
import com.baeldung.axon.coreapi.events.ProductAddedEvent;
import com.baeldung.axon.coreapi.events.ProductCountDecrementedEvent;
import com.baeldung.axon.coreapi.events.ProductCountIncrementedEvent;
import com.baeldung.axon.coreapi.events.ProductRemovedEvent;
import com.baeldung.axon.coreapi.queries.FindAllOrderedProductsQuery;
import com.baeldung.axon.coreapi.queries.OrderedProduct;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@ProcessingGroup("ordered-products")
public class OrderedProductsEventHandler {

    private final Map<String, OrderedProduct> orderedProducts = new HashMap<>();

    @EventHandler
    public void on(OrderPlacedEvent event) {
        String orderId = event.getOrderId();
        orderedProducts.put(orderId, new OrderedProduct(orderId));
    }

    @EventHandler
    public void on(ProductAddedEvent event) {
        orderedProducts.computeIfPresent(event.getOrderId(), (orderId, orderedProduct) -> {
            orderedProduct.addProduct(event.getProductId());
            return orderedProduct;
        });
    }

    @EventHandler
    public void on(ProductCountIncrementedEvent event) {
        orderedProducts.computeIfPresent(event.getOrderId(), (orderId, orderedProduct) -> {
            orderedProduct.incrementProductInstance(event.getProductId());
            return orderedProduct;
        });
    }

    @EventHandler
    public void on(ProductCountDecrementedEvent event) {
        orderedProducts.computeIfPresent(event.getOrderId(), (orderId, orderedProduct) -> {
            orderedProduct.decrementProductInstance(event.getProductId());
            return orderedProduct;
        });
    }

    @EventHandler
    public void on(ProductRemovedEvent event) {
        orderedProducts.computeIfPresent(event.getOrderId(), (orderId, orderedProduct) -> {
            orderedProduct.removeProduct(event.getProductId());
            return orderedProduct;
        });
    }

    @EventHandler
    public void on(OrderConfirmedEvent event) {
        orderedProducts.computeIfPresent(event.getOrderId(), (orderId, orderedProduct) -> {
            orderedProduct.setOrderConfirmed();
            return orderedProduct;
        });
    }

    @EventHandler
    public void on(OrderShippedEvent event) {
        orderedProducts.computeIfPresent(event.getOrderId(), (orderId, orderedProduct) -> {
            orderedProduct.setOrderShipped();
            return orderedProduct;
        });
    }

    @QueryHandler
    public List<OrderedProduct> handle(FindAllOrderedProductsQuery query) {
        return new ArrayList<>(orderedProducts.values());
    }
}