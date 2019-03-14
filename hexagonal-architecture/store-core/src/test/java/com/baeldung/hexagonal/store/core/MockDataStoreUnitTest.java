package com.baeldung.hexagonal.store.core;

import com.baeldung.hexagonal.store.core.context.customer.infrastructure.CustomerDataStore;
import com.baeldung.hexagonal.store.core.context.order.entity.Order;
import com.baeldung.hexagonal.store.core.context.order.infrastructure.EmailNotificationSender;
import com.baeldung.hexagonal.store.core.context.order.infrastructure.OrderDataStore;
import com.baeldung.hexagonal.store.core.context.order.infrastructure.ProductDataStore;
import com.baeldung.hexagonal.store.core.context.order.service.OrderServiceImpl;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MockDataStoreUnitTest {


    @Test
    public void givenMockedRepo_whenCalledGetAll_thenReturnMockedData() {
        // Arrange
        EmailNotificationSender emailSender = mock(EmailNotificationSender.class);
        OrderDataStore orderDataStore = mock(OrderDataStore.class);
        CustomerDataStore customerDataStore = mock(CustomerDataStore.class);
        ProductDataStore productDataStore = mock(ProductDataStore.class);
        OrderServiceImpl orderService = new OrderServiceImpl(customerDataStore, orderDataStore, productDataStore, emailSender);
        long orderId = 2323L;
        String status = "Pending";
        Order order = createFakeOrder(orderId, status);
        when(orderDataStore.findAll()).thenReturn(Collections.singletonList(order));
        // Act

        Iterable<Order> orders = orderService.getAllOrders();

        // Assert
        assertTrue(orders.iterator().hasNext());
        Order firstItem = orders.iterator().next();
        assertNotNull(firstItem);
        assertEquals(orderId, (long) firstItem.getId());
        assertEquals(status, firstItem.getStatus());
    }

    private static Order createFakeOrder(long id, String status) {
        Order order = new Order();
        order.setId(id);
        order.setStatus(status);
        return order;
    }

}
