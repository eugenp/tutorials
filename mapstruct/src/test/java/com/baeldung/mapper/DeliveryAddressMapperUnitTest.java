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

        // given
        Customer customer = new Customer().setFirstName("Max")
            .setLastName("Powers");

        Address homeAddress = new Address().setStreet("123 Some Street")
            .setCounty("Nevada")
            .setPostalcode("89123");

        // when
        DeliveryAddress deliveryAddress = deliveryAddressMapper.from(customer, homeAddress);

        // then
        assertEquals(deliveryAddress.getForename(), customer.getFirstName());
        assertEquals(deliveryAddress.getSurname(), customer.getLastName());
        assertEquals(deliveryAddress.getStreet(), homeAddress.getStreet());
        assertEquals(deliveryAddress.getCounty(), homeAddress.getCounty());
        assertEquals(deliveryAddress.getPostalcode(), homeAddress.getPostalcode());
    }

    @Test
    public void testGivenDeliveryAddressAndSomeOtherAddress_updatesDeliveryAddress() {

        // given
        Customer customer = new Customer().setFirstName("Max")
            .setLastName("Powers");

        DeliveryAddress deliveryAddress = new DeliveryAddress().setStreet("123 Some Street")
            .setCounty("Nevada")
            .setPostalcode("89123");

        Address otherAddress = new Address().setStreet("456 Some other street")
            .setCounty("Arizona")
            .setPostalcode("12345");

        // when
        DeliveryAddress updatedDeliveryAddress = deliveryAddressMapper.updateAddress(deliveryAddress, otherAddress);

        // then
        assertSame(deliveryAddress, updatedDeliveryAddress);

        assertEquals(deliveryAddress.getStreet(), otherAddress.getStreet());
        assertEquals(deliveryAddress.getCounty(), otherAddress.getCounty());
        assertEquals(deliveryAddress.getPostalcode(), otherAddress.getPostalcode());
    }
}
