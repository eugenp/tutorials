package com.baeldung.axon.querymodel;

import com.baeldung.axon.coreapi.events.OrderConfirmedEvent;
import com.baeldung.axon.coreapi.events.OrderCreatedEvent;
import com.baeldung.axon.coreapi.events.OrderShippedEvent;
import com.baeldung.axon.coreapi.events.ProductAddedEvent;
import com.baeldung.axon.coreapi.events.ProductCountDecrementedEvent;
import com.baeldung.axon.coreapi.events.ProductCountIncrementedEvent;
import com.baeldung.axon.coreapi.queries.FindAllOrderedProductsQuery;
import com.baeldung.axon.coreapi.queries.Order;
import com.baeldung.axon.coreapi.queries.OrderStatus;
import com.baeldung.axon.coreapi.queries.OrderUpdatesQuery;
import com.baeldung.axon.coreapi.queries.TotalProductsShippedQuery;
import org.axonframework.queryhandling.QueryUpdateEmitter;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public abstract class AbstractOrdersEventHandlerTest {

    private static final String ORDER_ID_1 = UUID.randomUUID().toString();
    private static final String ORDER_ID_2 = UUID.randomUUID().toString();
    private static final String PRODUCT_ID_1 = UUID.randomUUID().toString();
    private static final String PRODUCT_ID_2 = UUID.randomUUID().toString();
    private OrdersEventHandler handler;
    QueryUpdateEmitter emitter = mock(QueryUpdateEmitter.class);

    @BeforeEach
    void setUp() {
        handler = getHandler();
    }

    protected abstract OrdersEventHandler getHandler();

    @Test
    void findAllOrderedProductsQueryTest() {
        handler.on(new OrderCreatedEvent(ORDER_ID_1));
        handler.on(new ProductAddedEvent(ORDER_ID_1, PRODUCT_ID_1));
        handler.on(new OrderConfirmedEvent(ORDER_ID_1));
        handler.on(new OrderCreatedEvent(ORDER_ID_2));
        handler.on(new ProductAddedEvent(ORDER_ID_2, PRODUCT_ID_2));

        List<Order> result = handler.handle(new FindAllOrderedProductsQuery());

        assertNotNull(result);
        assertEquals(2, result.size());

        Order order_1 = result.stream().filter(o -> o.getOrderId().equals(ORDER_ID_1)).findFirst().orElseThrow();
        assertEquals(1, order_1.getProducts().size());
        assertEquals(1, order_1.getProducts().get(PRODUCT_ID_1));
        assertEquals(OrderStatus.CONFIRMED, order_1.getOrderStatus());

        Order order_2 = result.stream().filter(o -> o.getOrderId().equals(ORDER_ID_2)).findFirst().orElseThrow();
        assertEquals(1, order_2.getProducts().size());
        assertEquals(1, order_2.getProducts().get(PRODUCT_ID_2));
        assertEquals(OrderStatus.CREATED, order_2.getOrderStatus());
    }

    @Test
    void totalShippedProductsQueryTest() {
        handler.on(new OrderCreatedEvent(ORDER_ID_1));
        handler.on(new ProductAddedEvent(ORDER_ID_1, PRODUCT_ID_1));
        handler.on(new ProductCountIncrementedEvent(ORDER_ID_1, PRODUCT_ID_1));
        handler.on(new ProductCountIncrementedEvent(ORDER_ID_1, PRODUCT_ID_1));
        handler.on(new OrderConfirmedEvent(ORDER_ID_1));
        handler.on(new OrderCreatedEvent(ORDER_ID_2));
        handler.on(new ProductAddedEvent(ORDER_ID_2, PRODUCT_ID_1));
        handler.on(new ProductAddedEvent(ORDER_ID_2, PRODUCT_ID_2));
        handler.on(new OrderConfirmedEvent(ORDER_ID_1));

        assertEquals(0, handler.handle(new TotalProductsShippedQuery(PRODUCT_ID_1)));
        assertEquals(0, handler.handle(new TotalProductsShippedQuery(PRODUCT_ID_2)));

        handler.on(new OrderShippedEvent(ORDER_ID_1));
        assertEquals(3, handler.handle(new TotalProductsShippedQuery(PRODUCT_ID_1)));
        assertEquals(0, handler.handle(new TotalProductsShippedQuery(PRODUCT_ID_2)));

        handler.on(new OrderShippedEvent(ORDER_ID_2));
        assertEquals(4, handler.handle(new TotalProductsShippedQuery(PRODUCT_ID_1)));
        assertEquals(1, handler.handle(new TotalProductsShippedQuery(PRODUCT_ID_2)));
    }

    @Test
    void orderStatusUpdatesQueryTest(){
        handler.on(new OrderCreatedEvent(ORDER_ID_1));
        handler.on(new ProductAddedEvent(ORDER_ID_1, PRODUCT_ID_1));

        Order result = handler.handle(new OrderUpdatesQuery(ORDER_ID_1));
        assertNotNull(result);
        assertEquals(ORDER_ID_1, result.getOrderId());
        assertEquals(1, result.getProducts().get(PRODUCT_ID_1));
        assertEquals(OrderStatus.CREATED, result.getOrderStatus());
        verify(emitter, times(1)).emit(eq(OrderUpdatesQuery.class), any(), any(Order.class));

        handler.on(new ProductCountIncrementedEvent(ORDER_ID_1, PRODUCT_ID_1));
        result = handler.handle(new OrderUpdatesQuery(ORDER_ID_1));
        assertNotNull(result);
        assertEquals(2, result.getProducts().get(PRODUCT_ID_1));
        verify(emitter, times(2)).emit(eq(OrderUpdatesQuery.class), any(), any(Order.class));

        handler.on(new ProductCountDecrementedEvent(ORDER_ID_1, PRODUCT_ID_1));
        result = handler.handle(new OrderUpdatesQuery(ORDER_ID_1));
        assertNotNull(result);
        assertEquals(1, result.getProducts().get(PRODUCT_ID_1));
        verify(emitter, times(3)).emit(eq(OrderUpdatesQuery.class), any(), any(Order.class));

        handler.on(new OrderConfirmedEvent(ORDER_ID_1));
        result = handler.handle(new OrderUpdatesQuery(ORDER_ID_1));
        assertNotNull(result);
        assertEquals(OrderStatus.CONFIRMED, result.getOrderStatus());
        verify(emitter, times(4)).emit(eq(OrderUpdatesQuery.class), any(), any(Order.class));

        handler.on(new OrderShippedEvent(ORDER_ID_1));
        result = handler.handle(new OrderUpdatesQuery(ORDER_ID_1));
        assertNotNull(result);
        assertEquals(OrderStatus.SHIPPED, result.getOrderStatus());
        verify(emitter, times(5)).emit(eq(OrderUpdatesQuery.class), any(), any(Order.class));
    }
}
