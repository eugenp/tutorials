package com.baeldung.cloud.openfeign;

import com.baeldung.cloud.openfeign.client.PaymentClient;
import com.baeldung.cloud.openfeign.model.Payment;
import com.baeldung.cloud.openfeign.model.Post;
import com.baeldung.cloud.openfeign.service.JSONPlaceHolderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertFalse;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PaymentClientUnitTest {

    @Autowired
    private PaymentClient paymentClient;

    @Test
    public void whenGetPayment_thenListPayments() {

        List<Payment> payments = paymentClient.getPayments();

        assertFalse(payments.isEmpty());
    }

}
