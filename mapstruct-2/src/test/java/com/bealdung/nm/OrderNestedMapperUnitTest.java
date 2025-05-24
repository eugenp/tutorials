package com.bealdung.nm;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.baeldung.nm.AbstractOrderMapper;
import com.baeldung.nm.OrderMapper;
import com.baeldung.nm.entity.Address;
import com.baeldung.nm.entity.Customer;
import com.baeldung.nm.entity.Order;
import com.baeldung.nm.entity.OrderDto;
import com.baeldung.nm.entity.Product;

public class OrderNestedMapperUnitTest {
     @Test
     void givenOrder_whenMapToOrderDto_thenMapNestedAttributes() {
         Order order = createSampleOrderObject();

         OrderDto orderDto = OrderMapper.INSTANCE.orderToOrderDto(order);

         assertEquals("John Doe", orderDto.getCustomerName());
         assertEquals("New York", orderDto.getCustomerCity());
         assertEquals("10001", orderDto.getCustomerZipCode());
         assertEquals("Laptop", orderDto.getProductName());
         assertEquals(1200.00, orderDto.getProductPrice());
     }

    @Test
    void givenOrder_whenMapToOrderDto_thenMapNestedAttributesWithAbstractMapper() {
        Order order = createSampleOrderObject();

        OrderDto orderDto = AbstractOrderMapper.INSTANCE.orderToOrderDto(order);

        assertEquals("John Doe", orderDto.getCustomerName());
        assertEquals("New York", orderDto.getCustomerCity());
        assertEquals("10001", orderDto.getCustomerZipCode());
        assertEquals("Laptop", orderDto.getProductName());
        assertEquals(1200.00, orderDto.getProductPrice());
    }

    private Order createSampleOrderObject() {
        Order order = new Order();

        order.setCustomer(new Customer());
        order.getCustomer().setName("John Doe");

        order.getCustomer().setAddress(new Address());
        order.getCustomer().getAddress().setCity("New York");
        order.getCustomer().getAddress().setZipCode("10001");

        order.setProduct(new Product());
        order.getProduct().setName("Laptop");
        order.getProduct().setPrice(1200.00);
        return order;
    }

}
