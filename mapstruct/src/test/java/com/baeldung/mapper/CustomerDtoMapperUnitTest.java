package com.baeldung.mapper;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.baeldung.context.MappingContext;
import com.baeldung.dto.CustomerDto;
import com.baeldung.entity.Customer;

public class CustomerDtoMapperUnitTest {

    private CustomerDtoMapper customerDtoMapper = Mappers.getMapper(CustomerDtoMapper.class);

    @Test
    void testGivenCustomer_mapsToCustomerDto() {

        // given
        Customer customer = new Customer().setFirstName("Max")
            .setLastName("Powers");

        // when
        CustomerDto customerDto = customerDtoMapper.from(customer);

        // then
        assertEquals(customerDto.getForename(), customer.getFirstName());
        assertEquals(customerDto.getSurname(), customer.getLastName());
    }

    @Test
    void givenCustomer_whenMappedUsingContext_thenReturnsFormattedDto() {
        Customer customer = new Customer();
        customer.setFirstName(" max ");
        customer.setLastName(" powers ");
        MappingContext context = new MappingContext();
        CustomerDto dto = customerDtoMapper.from(customer, context);
        assertEquals("MAX", dto.getForename());
        assertEquals("POWERS", dto.getSurname());
    }
}
