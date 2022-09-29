package com.baeldung.deepcopy2;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.commons.lang3.SerializationUtils;
import org.junit.jupiter.api.Test;

public class DeepCopyUnitTest {

    @Test
    public void whenCopyWithCopyConstructor_thenObjectShouldBeDifferent() {
        Customer customer = new Customer("Mark", "mark@email.com");
        Payment payment = new Payment(20.00, "SKU-1", customer);

        Payment paymentCopy = new Payment(payment);

        assertThat(payment).isNotSameAs(paymentCopy);
    }

    @Test
    public void whenModifyingOriginal_thenCopyWithCopyConstructorShouldNotChange(){
        Customer customer = new Customer("Mark", "mark@email.com");
        Payment payment = new Payment(20.00, "SKU-1", customer);

        Payment paymentCopy = new Payment(payment);

        customer.username = "John";

        assertThat(paymentCopy.customer.username).isNotEqualTo("John");
    }

    @Test
    public void whenModifyingOriginal_thenApacheCommonsCloneShouldNotChange() {
        Customer customer = new Customer("Mark", "mark@email.com");
        Payment payment = new Payment(20.00, "SKU-1", customer);

        Payment paymentCopy = SerializationUtils.clone(payment);

        customer.username = "John";

        assertThat(paymentCopy.customer.username).isNotEqualTo(payment.customer.username);
    }
}
