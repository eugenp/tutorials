package com.baeldung.boot.composite.key.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.boot.composite.key.dao.CustomerRepository;
import com.baeldung.boot.composite.key.dao.SaleRepository;
import com.baeldung.boot.composite.key.dao.TicketRepository;
import com.baeldung.boot.composite.key.data.Customer;
import com.baeldung.boot.composite.key.data.Sale;
import com.baeldung.boot.composite.key.data.Ticket;
import com.baeldung.boot.composite.key.data.TicketId;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private SaleRepository saleRepository;

    public Optional<Ticket> find(TicketId id) {
        return ticketRepository.findById(id);
    }

    public Ticket insert(Ticket ticket) {
        return ticketRepository.insert(ticket);
    }

    public Ticket save(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    public Optional<Customer> findCustomerById(String id) {
        return customerRepository.findById(id);
    }

    public Optional<Customer> findCustomerByIndex(Long storeId, Long number) {
        return customerRepository.findByStoreIdAndNumber(storeId, number);
    }

    public Customer insert(Customer customer) {
        return customerRepository.insert(customer);
    }

    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    public Sale insert(Sale sale) {
        return saleRepository.insert(sale);
    }

    public Optional<Sale> findSaleByTicketId(TicketId ticketId) {
        return saleRepository.findByTicketId(ticketId);
    }

    public Optional<Sale> findSaleById(String id) {
        return saleRepository.findById(id);
    }
}
