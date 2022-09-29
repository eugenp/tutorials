package com.baeldung.deepcopy2;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class ShallowCopyUnitTest {

    @Test
    public void whenCreatingCopyWithClone_thenObjectsShouldNotBeSame() {
        Customer customer = new Customer("Mark", "mark@email.com");
        Payment payment = new Payment(20.00, "SKU-1", customer);

        Payment paymentCopy = payment.clone();

        assertThat(payment).isNotSameAs(paymentCopy);

    }

    @Test
    public void whenChangingOriginalObject_thenShallowCopyWillAlsoBeChanged() {

        Customer customer = new Customer("Mark", "mark@email.com");
        Payment payment = new Payment(20.00, "SKU-1", customer);

        Payment paymentCopy = payment.clone();

        customer.username = "John";

        assertThat(paymentCopy.customer.username).isEqualTo("John");
    }

}
