package com.baeldung.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.model.Customer;
import com.baeldung.model.CustomerDto;
import com.baeldung.repository.CustomerRepository;
import com.baeldung.util.CustomerMapper;

@Service @Transactional
public class CustomerService {

    @Autowired CustomerRepository repo;
    @Autowired CustomerMapper mapper;

    public Customer addCustomer(String firstName, String lastName) {
        Customer myCustomer = new Customer();
        myCustomer.firstName = firstName;
        myCustomer.lastName = lastName;
        repo.save(myCustomer);
        return myCustomer;
    }

    public Customer updateCustomer(long id, String email, String phone, String address) {
        Customer myCustomer = repo.findById(id);
        myCustomer.address = address;
        myCustomer.email = email;
        myCustomer.phone = phone;
        repo.save(myCustomer);
        return myCustomer;
    }

    public Customer addCustomer(CustomerDto dto) {
        Customer myCustomer = new Customer();
        mapper.updateCustomerFromDto(dto, myCustomer);
        repo.save(myCustomer);
        return myCustomer;
    }

    public Customer updateCustomer(CustomerDto dto) {
        Customer myCustomer = repo.findById(dto.id);
        mapper.updateCustomerFromDto(dto, myCustomer);
        repo.save(myCustomer);
        return myCustomer;
    }

}
