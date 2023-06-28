package com.baeldung.hibernate.onetomany.collection.listvsset;

import java.util.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@RunWith(SpringRunner.class)
public class CustomerUnitTest {

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void givenCustomers_whenFetchMultipleLists_thenThrowsException() {
        CustomerEntity customer = new CustomerEntity();
        OrderEntity order1 = new OrderEntity("order");
        OrderEntity order2 = new OrderEntity("order");
        AddressEntity address1 = new AddressEntity("address");
        AddressEntity address2 = new AddressEntity("address");

        customer.getOrderList().addAll(Arrays.asList(order1, order2));
        customer.getAddressList().addAll(Arrays.asList(address1, address2));

        entityManager.persist(customer);
        entityManager.flush();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            entityManager.getEntityManager()
                    .createQuery("SELECT c FROM CustomerEntity c JOIN FETCH c.orderList JOIN FETCH c.addressList", CustomerEntity.class)
                    .getResultList();
        }, "Expected to throw an exception when fetching multiple lists in a single query");

        assertThat(exception.getMessage()).contains("cannot simultaneously fetch multiple bags");
    }
}