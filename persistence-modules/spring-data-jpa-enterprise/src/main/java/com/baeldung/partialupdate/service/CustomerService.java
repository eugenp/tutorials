package com.baeldung.partialupdate.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.partialupdate.model.ContactPhone;
import com.baeldung.partialupdate.model.Customer;
import com.baeldung.partialupdate.model.CustomerDto;
import com.baeldung.partialupdate.model.CustomerStructured;
import com.baeldung.partialupdate.repository.ContactPhoneRepository;
import com.baeldung.partialupdate.repository.CustomerRepository;
import com.baeldung.partialupdate.repository.CustomerStructuredRepository;
import com.baeldung.partialupdate.util.CustomerMapper;

@Service 
@Transactional
public class CustomerService {

    @Autowired 
    CustomerRepository repo;
    @Autowired 
    CustomerStructuredRepository repo2;
    @Autowired 
    ContactPhoneRepository repo3;
    @Autowired 
    CustomerMapper mapper;

    public Customer getCustomer(long id) {
        return repo.findById(id);
    }

    public void updateCustomerWithCustomQuery(long id, String phone) {
        repo.updatePhone(id, phone);
    }

    public Customer addCustomer(String name) {
        Customer myCustomer = new Customer();
        myCustomer.name = name;
        repo.save(myCustomer);
        return myCustomer;
    }

    public Customer updateCustomer(long id, String phone) {
        Customer myCustomer = repo.findById(id);
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
        Customer myCustomer = repo.findById(dto.getId());
        mapper.updateCustomerFromDto(dto, myCustomer);
        repo.save(myCustomer);
        return myCustomer;
    }

    public CustomerStructured addCustomerStructured(String name) {
        CustomerStructured myCustomer = new CustomerStructured();
        myCustomer.name = name;
        repo2.save(myCustomer);
        return myCustomer;
    }

    public void addCustomerPhone(long customerId, String phone) {
        ContactPhone myPhone = new ContactPhone();
        myPhone.phone = phone;
        myPhone.customerId =  customerId;
        repo3.save(myPhone);
    }
    
    public CustomerStructured updateCustomerStructured(long id, String name) {
        CustomerStructured myCustomer = repo2.findById(id);
        myCustomer.name = name;
        repo2.save(myCustomer);
        return myCustomer;
    }

}
