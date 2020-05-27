package com.baeldung.orderservice;

import com.baeldung.paymentservice.PaymentClient;
import com.baeldung.paymentservice.PaymentDTO;
import com.baeldung.paymentservice.PaymentResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = OrderApplication.class)
public class OrderServiceTest {

    @Autowired
    private PaymentClient paymentClient;


    @Test
    public void testSendPaySuccess(){


        PaymentDTO dto = new PaymentDTO("Sasa","Milenkovic","4260-6720-3283-7081",150.0,"USD");

        PaymentResponse response = paymentClient.pay("A152", dto);

        Assert.assertNotNull("Payment Id not generated", response.getPaymentId());
        Assert.assertEquals("CREDITCARD", response.getPaymentMethod());
        Assert.assertEquals("Sasa Milenkovic", response.getCustomerFullName());
        Assert.assertEquals(new Double(150.0), response.getAmount());
        Assert.assertEquals("USD", response.getCurrency());
    }
}
