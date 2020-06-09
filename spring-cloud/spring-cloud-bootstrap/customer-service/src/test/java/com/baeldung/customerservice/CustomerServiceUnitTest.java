package com.baeldung.customerservice;

import com.baeldung.orderservice.client.OrderClient;
import com.baeldung.orderservice.client.OrderDTO;
import com.baeldung.orderservice.client.OrderResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CustomerApplication.class)
public class CustomerServiceUnitTest {

    @Autowired
    private OrderClient orderClient;

    @Test
    public void testAddOrderSuccess(){


        OrderDTO dto = new OrderDTO(2,"A152");

        OrderResponse response = orderClient.order(dto);

        Assert.assertNotNull("Order Id not generated", response.getOrderId());
        Assert.assertEquals("A152", response.getProductId());
        Assert.assertEquals("CREATED", response.getStatus());
    }

}
