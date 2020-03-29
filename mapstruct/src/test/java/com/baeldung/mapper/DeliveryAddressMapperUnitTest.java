package com.baeldung.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import org.junit.Test;
import org.mapstruct.factory.Mappers;

import com.baeldung.entity.Address;
import com.baeldung.entity.Customer;
import com.baeldung.entity.DeliveryAddress;

public class DeliveryAddressMapperUnitTest {

    private DeliveryAddressMapper deliveryAddressMapper = Mappers.getMapper(DeliveryAddressMapper.class);

    @Test
    public void testGivenCustomerAndAddress_mapsToDeliveryAddress() {

        // given a customer
        Customer customer = new Customer().setFirstName("Max")
            .setLastName("Powers");

        // and some address
        Address homeAddress = new Address().setStreet("123 Some Street")
            .setCounty("Nevada")
            .setPostalcode("89123");

        // when calling DeliveryAddressMapper::from
        DeliveryAddress deliveryAddress = deliveryAddressMapper.from(customer, homeAddress);

        // then a new DeliveryAddress is created, based on the given customer and his home address
        assertEquals(deliveryAddress.getForename(), customer.getFirstName());
        assertEquals(deliveryAddress.getSurname(), customer.getLastName());
        assertEquals(deliveryAddress.getStreet(), homeAddress.getStreet());
        assertEquals(deliveryAddress.getCounty(), homeAddress.getCounty());
        assertEquals(deliveryAddress.getPostalcode(), homeAddress.getPostalcode());

    }

    @Test
    public void testGivenDeliveryAddressAndSomeOtherAddress_updatesDeliveryAddress() {

        // given a delivery address
        DeliveryAddress deliveryAddress = new DeliveryAddress().setForename("Max")
            .setSurname("Powers")
            .setStreet("123 Some Street")
            .setCounty("Nevada")
            .setPostalcode("89123");

        // and some new address
        Address newAddress = new Address().setStreet("456 Some other street")
            .setCounty("Arizona")
            .setPostalcode("12345");

        // when calling DeliveryAddressMapper::updateAddress
        DeliveryAddress updatedDeliveryAddress = deliveryAddressMapper.updateAddress(deliveryAddress, newAddress);

        // then the *existing* delivery address is updated
        assertSame(deliveryAddress, updatedDeliveryAddress);

        assertEquals(deliveryAddress.getStreet(), newAddress.getStreet());
        assertEquals(deliveryAddress.getCounty(), newAddress.getCounty());
        assertEquals(deliveryAddress.getPostalcode(), newAddress.getPostalcode());

    }
}
