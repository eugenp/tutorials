package com.baeldung.cloud.openfeign;

import com.baeldung.cloud.openfeign.client.PaymentClient;
import com.baeldung.cloud.openfeign.model.Payment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertFalse;

/**
 * This test can be used to verify OAuth2 Token functionality with Feign client
 * (https://www.baeldung.com/spring-cloud-feign-oauth-token).
 *
 * The following components should be setup and running, as described in the article, prior to running this test.
 *
 * Authorization Server - embedded Keycloak server with the correct client and client-secret defined in the master realm.
 * This will issue the auth tokens used by Feign client.
 * Further details are available at: https://www.baeldung.com/keycloak-embedded-in-spring-boot-app
 *
 * Resource Server - OAuth resource server requiring valid JWT token (issued by the Authorization Server).
 * Further details are available at: https://www.baeldung.com/spring-security-oauth-resource-server
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OpenFeignOAuth2ManualTest {

    @Autowired
    private PaymentClient paymentClient;

    @Test
    public void whenGetPayment_thenListPayments() {

        List<Payment> payments = paymentClient.getPayments();

        assertFalse(payments.isEmpty());
    }

}
