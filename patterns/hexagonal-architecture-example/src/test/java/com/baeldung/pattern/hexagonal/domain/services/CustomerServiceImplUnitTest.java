package com.baeldung.pattern.hexagonal.domain.services;

import com.baeldung.pattern.hexagonal.domain.model.Customer;
import com.baeldung.pattern.hexagonal.domain.model.Address;
import com.baeldung.pattern.hexagonal.persistence.CustomerRepository;
import com.baeldung.pattern.hexagonal.persistence.AddressRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CustomerServiceImplUnitTest {

    private CustomerRepository customerRepository;
    private AddressRepository addressRepository;
    private CustomerService testService;
    private Customer testModel;
    private Address testAddress;

    @BeforeEach
    void setUp() {
    	customerRepository = mock(CustomerRepository.class);
    	addressRepository = mock(AddressRepository.class);
        testService = new CustomerServiceImpl(customerRepository, addressRepository);
        testModel = new Customer();
        testModel.setUserId("testCustomer");
        testModel.setName("TestFirstName LastName");
        testModel.setPhone("2035559876");
        testAddress = new Address();
        testAddress.setAddressId("testAddressId");
        testAddress.setAddressType("HOME");
        testModel.setHomeAddress(testAddress);
    }

    @Test
    void addCustomer() {
        when(customerRepository.add(any(Customer.class))).thenReturn(testModel);
        when(addressRepository.save(any(Address.class))).thenReturn(testAddress);

        Customer testResponse = testService.addCustomer(testModel);
        Address testAddressResponse = testService.addAddress(testAddress);
        assertEquals(testModel, testResponse);
        assertEquals(testModel.getHomeAddress(), testAddressResponse);
    }

    @Test
    void getCustomer() {
        when(customerRepository.findByUserId("testCustomer")).thenReturn(Optional.of(testModel));
        when(addressRepository.findByAddressId("testAddressId")).thenReturn(Optional.of(testAddress));

        Customer testResponse = testService.getCustomer("testCustomer");
        Address testAddressResponse = testService.getAddress("testAddressId");
        assertEquals(testModel, testResponse);
        assertEquals(testResponse.getHomeAddress(), testAddressResponse);
    }
}