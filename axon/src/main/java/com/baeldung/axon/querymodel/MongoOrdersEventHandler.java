package com.baeldung.axon.querymodel;

import com.baeldung.axon.coreapi.events.OrderConfirmedEvent;
import com.baeldung.axon.coreapi.events.OrderCreatedEvent;
import com.baeldung.axon.coreapi.events.OrderShippedEvent;
import com.baeldung.axon.coreapi.events.ProductAddedEvent;
import com.baeldung.axon.coreapi.events.ProductCountDecrementedEvent;
import com.baeldung.axon.coreapi.events.ProductCountIncrementedEvent;
import com.baeldung.axon.coreapi.events.ProductRemovedEvent;
import com.baeldung.axon.coreapi.queries.FindAllOrderedProductsQuery;
import com.baeldung.axon.coreapi.queries.Order;
import com.baeldung.axon.coreapi.queries.OrderStatus;
import com.baeldung.axon.coreapi.queries.OrderUpdatesQuery;
import com.baeldung.axon.coreapi.queries.TotalProductsShippedQuery;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.result.UpdateResult;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.axonframework.queryhandling.QueryUpdateEmitter;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import static com.mongodb.client.model.Filters.*;

@Service
@ProcessingGroup("orders")
@Profile("mongo")
public class MongoOrdersEventHandler implements OrdersEventHandler {

    static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup()
      .lookupClass());

    private final MongoCollection<Document> orders;
    private final QueryUpdateEmitter emitter;
    private static final String ORDER_COLLECTION_NAME = "orders";
    private static final String AXON_FRAMEWORK_DATABASE_NAME = "axonframework";

    private static final String ORDER_ID_PROPERTY_NAME = "orderId";
    private static final String PRODUCTS_PROPERTY_NAME = "products";
    private static final String ORDER_STATUS_PROPERTY_NAME = "orderStatus";

    public MongoOrdersEventHandler(MongoClient client, QueryUpdateEmitter emitter) {
        orders = client.getDatabase(AXON_FRAMEWORK_DATABASE_NAME)
          .getCollection(ORDER_COLLECTION_NAME);
        orders.createIndex(Indexes.ascending(ORDER_ID_PROPERTY_NAME), new IndexOptions().unique(true));
        this.emitter = emitter;
    }

    @EventHandler
    public void on(OrderCreatedEvent event) {
        orders.insertOne(orderToDocument(new Order(event.getOrderId())));
    }

    @EventHandler
    public void on(ProductAddedEvent event) {
        update(event.getOrderId(), o -> o.addProduct(event.getProductId()));
    }

    @EventHandler
    public void on(ProductCountIncrementedEvent event) {
        update(event.getOrderId(), o -> o.incrementProductInstance(event.getProductId()));
    }

    @EventHandler
    public void on(ProductCountDecrementedEvent event) {
        update(event.getOrderId(), o -> o.decrementProductInstance(event.getProductId()));
    }

    @EventHandler
    public void on(ProductRemovedEvent event) {
        update(event.getOrderId(), o -> o.removeProduct(event.getProductId()));
    }

    @EventHandler
    public void on(OrderConfirmedEvent event) {
        update(event.getOrderId(), Order::setOrderConfirmed);
    }

    @EventHandler
    public void on(OrderShippedEvent event) {
        update(event.getOrderId(), Order::setOrderShipped);
    }

    @QueryHandler
    public List<Order> handle(FindAllOrderedProductsQuery query) {
        List<Order> orderList = new ArrayList<>();
        orders.find()
          .forEach(d -> orderList.add(documentToOrder(d)));
        return orderList;
    }

    @Override
    public Publisher<Order> handleStreaming(FindAllOrderedProductsQuery query) {
        return Flux.fromIterable(orders.find())
          .map(this::documentToOrder);
    }

    @QueryHandler
    public Integer handle(TotalProductsShippedQuery query) {
        AtomicInteger result = new AtomicInteger();
        orders.find(shippedProductFilter(query.getProductId()))
          .map(d -> d.get(PRODUCTS_PROPERTY_NAME, Document.class))
          .map(d -> d.getInteger(query.getProductId(), 0))
          .forEach(result::addAndGet);
        return result.get();
    }

    @QueryHandler
    public Order handle(OrderUpdatesQuery query) {
        return getOrder(query.getOrderId()).orElse(null);
    }

    @Override
    public void reset(List<Order> orderList) {
        orders.deleteMany(new Document());
        orderList.forEach(o -> orders.insertOne(orderToDocument(o)));
    }

    private Optional<Order> getOrder(String orderId) {
        return Optional.ofNullable(orders.find(eq(ORDER_ID_PROPERTY_NAME, orderId))
            .first())
          .map(this::documentToOrder);
    }

    private Order emitUpdate(Order order) {
        emitter.emit(OrderUpdatesQuery.class, q -> order.getOrderId()
          .equals(q.getOrderId()), order);
        return order;
    }

    private Order updateOrder(Order order, Consumer<Order> updateFunction) {
        updateFunction.accept(order);
        return order;
    }

    private UpdateResult persistUpdate(Order order) {
        return orders.replaceOne(eq(ORDER_ID_PROPERTY_NAME, order.getOrderId()), orderToDocument(order));
    }

    private void update(String orderId, Consumer<Order> updateFunction) {
        UpdateResult result = getOrder(orderId).map(o -> updateOrder(o, updateFunction))
          .map(this::emitUpdate)
          .map(this::persistUpdate)
          .orElse(null);
        logger.info("Result of updating order with orderId '{}': {}", orderId, result);
    }

    private Document orderToDocument(Order order) {
        return new Document(ORDER_ID_PROPERTY_NAME, order.getOrderId()).append(PRODUCTS_PROPERTY_NAME, order.getProducts())
          .append(ORDER_STATUS_PROPERTY_NAME, order.getOrderStatus()
            .toString());
    }

    private Order documentToOrder(@NotNull Document document) {
        Order order = new Order(document.getString(ORDER_ID_PROPERTY_NAME));
        Document products = document.get(PRODUCTS_PROPERTY_NAME, Document.class);
        products.forEach((k, v) -> order.getProducts()
          .put(k, (Integer) v));
        String status = document.getString(ORDER_STATUS_PROPERTY_NAME);
        if (OrderStatus.CONFIRMED.toString()
          .equals(status)) {
            order.setOrderConfirmed();
        } else if (OrderStatus.SHIPPED.toString()
          .equals(status)) {
            order.setOrderShipped();
        }
        return order;
    }

    private Bson shippedProductFilter(String productId) {
        return and(eq(ORDER_STATUS_PROPERTY_NAME, OrderStatus.SHIPPED.toString()), exists(String.format(PRODUCTS_PROPERTY_NAME + ".%s", productId)));
    }
}