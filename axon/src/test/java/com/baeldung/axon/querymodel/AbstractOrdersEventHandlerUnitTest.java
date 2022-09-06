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
import org.axonframework.queryhandling.QueryUpdateEmitter;
import org.junit.jupiter.api.*;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public abstract class AbstractOrdersEventHandlerUnitTest {

    private static final String ORDER_ID_1 = UUID.randomUUID().toString();
    private static final String ORDER_ID_2 = UUID.randomUUID().toString();
    private static final String PRODUCT_ID_1 = UUID.randomUUID().toString();
    private static final String PRODUCT_ID_2 = UUID.randomUUID().toString();
    private OrdersEventHandler handler;
    private static Order orderOne;
    private static Order orderTwo;
    QueryUpdateEmitter emitter = mock(QueryUpdateEmitter.class);

    @BeforeAll
    static void createOrders() {
        orderOne = new Order(ORDER_ID_1);
        orderOne.getProducts().put(PRODUCT_ID_1, 3);
        orderOne.setOrderShipped();

        orderTwo = new Order(ORDER_ID_2);
        orderTwo.getProducts().put(PRODUCT_ID_1, 1);
        orderTwo.getProducts().put(PRODUCT_ID_2, 1);
        orderTwo.setOrderConfirmed();
    }

    @BeforeEach
    void setUp() {
        handler = getHandler();
    }

    protected abstract OrdersEventHandler getHandler();

    @Test
    void givenTwoOrdersPlacedOfWhichOneNotShipped_whenFindAllOrderedProductsQuery_thenCorrectOrdersAreReturned() {
        resetWithTwoOrders();

        List<Order> result = handler.handle(new FindAllOrderedProductsQuery());

        assertNotNull(result);
        assertEquals(2, result.size());

        Order order_1 = result.stream().filter(o -> o.getOrderId().equals(ORDER_ID_1)).findFirst().orElse(null);
        assertEquals(orderOne, order_1);

        Order order_2 = result.stream().filter(o -> o.getOrderId().equals(ORDER_ID_2)).findFirst().orElse(null);
        assertEquals(orderTwo, order_2);
    }

    @Test
    void givenNoOrdersPlaced_whenTotalProductsShippedQuery_thenZeroReturned() {
        assertEquals(0, handler.handle(new TotalProductsShippedQuery(PRODUCT_ID_1)));
    }

    @Test
    void givenTwoOrdersPlacedOfWhichOneNotShipped_whenTotalProductsShippedQuery_thenOnlyCountProductsFirstOrder() {
        resetWithTwoOrders();

        assertEquals(3, handler.handle(new TotalProductsShippedQuery(PRODUCT_ID_1)));
        assertEquals(0, handler.handle(new TotalProductsShippedQuery(PRODUCT_ID_2)));
    }

    @Test
    void givenTwoOrdersPlacedAndShipped_whenTotalProductsShippedQuery_thenCountBothOrders() {
        resetWithTwoOrders();
        handler.on(new OrderShippedEvent(ORDER_ID_2));

        assertEquals(4, handler.handle(new TotalProductsShippedQuery(PRODUCT_ID_1)));
        assertEquals(1, handler.handle(new TotalProductsShippedQuery(PRODUCT_ID_2)));
    }

    @Test
    void givenOrderExist_whenOrderUpdatesQuery_thenOrderReturned() {
        resetWithTwoOrders();

        Order result = handler.handle(new OrderUpdatesQuery(ORDER_ID_1));
        assertNotNull(result);
        assertEquals(ORDER_ID_1, result.getOrderId());
        assertEquals(3, result.getProducts().get(PRODUCT_ID_1));
        assertEquals(OrderStatus.SHIPPED, result.getOrderStatus());
    }

    @Test
    void givenOrderExist_whenProductAddedEvent_thenUpdateEmittedOnce() {
        handler.on(new OrderCreatedEvent(ORDER_ID_1));

        handler.on(new ProductAddedEvent(ORDER_ID_1, PRODUCT_ID_1));

        verify(emitter, times(1)).emit(eq(OrderUpdatesQuery.class), any(), any(Order.class));
    }

    @Test
    void givenOrderWithProductExist_whenProductCountDecrementedEvent_thenUpdateEmittedOnce() {
        handler.on(new OrderCreatedEvent(ORDER_ID_1));
        handler.on(new ProductAddedEvent(ORDER_ID_1, PRODUCT_ID_1));
        reset(emitter);

        handler.on(new ProductCountDecrementedEvent(ORDER_ID_1, PRODUCT_ID_1));

        verify(emitter, times(1)).emit(eq(OrderUpdatesQuery.class), any(), any(Order.class));
    }

    @Test
    void givenOrderWithProductExist_whenProductRemovedEvent_thenUpdateEmittedOnce() {
        handler.on(new OrderCreatedEvent(ORDER_ID_1));
        handler.on(new ProductAddedEvent(ORDER_ID_1, PRODUCT_ID_1));
        reset(emitter);

        handler.on(new ProductRemovedEvent(ORDER_ID_1, PRODUCT_ID_1));

        verify(emitter, times(1)).emit(eq(OrderUpdatesQuery.class), any(), any(Order.class));
    }

    @Test
    void givenOrderWithProductExist_whenProductCountIncrementedEvent_thenUpdateEmittedOnce() {
        handler.on(new OrderCreatedEvent(ORDER_ID_1));
        handler.on(new ProductAddedEvent(ORDER_ID_1, PRODUCT_ID_1));
        reset(emitter);

        handler.on(new ProductCountIncrementedEvent(ORDER_ID_1, PRODUCT_ID_1));

        verify(emitter, times(1)).emit(eq(OrderUpdatesQuery.class), any(), any(Order.class));
    }

    @Test
    void givenOrderWithProductExist_whenOrderConfirmedEvent_thenUpdateEmittedOnce() {
        handler.on(new OrderCreatedEvent(ORDER_ID_1));
        handler.on(new ProductAddedEvent(ORDER_ID_1, PRODUCT_ID_1));
        reset(emitter);

        handler.on(new OrderConfirmedEvent(ORDER_ID_1));

        verify(emitter, times(1)).emit(eq(OrderUpdatesQuery.class), any(), any(Order.class));
    }

    @Test
    void givenOrderWithProductAndConfirmationExist_whenOrderShippedEvent_thenUpdateEmittedOnce() {
        handler.on(new OrderCreatedEvent(ORDER_ID_1));
        handler.on(new ProductAddedEvent(ORDER_ID_1, PRODUCT_ID_1));
        reset(emitter);

        handler.on(new OrderShippedEvent(ORDER_ID_1));

        verify(emitter, times(1)).emit(eq(OrderUpdatesQuery.class), any(), any(Order.class));
    }

    private void resetWithTwoOrders() {
        handler.reset(Arrays.asList(orderOne, orderTwo));
    }
}
