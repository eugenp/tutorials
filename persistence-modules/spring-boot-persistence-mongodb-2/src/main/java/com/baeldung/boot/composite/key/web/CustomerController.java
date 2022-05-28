package com.baeldung.boot.composite.key.web;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.boot.composite.key.data.Customer;
import com.baeldung.boot.composite.key.data.Sale;
import com.baeldung.boot.composite.key.data.Ticket;
import com.baeldung.boot.composite.key.data.TicketId;
import com.baeldung.boot.composite.key.service.CustomerService;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    // @Autowired
    // private TicketRepository ticketRepository;
    //
    // @GetMapping("/ticket")
    // public Optional<Ticket> getTicket(TicketId id) {
    // return ticketRepository.findById(id);
    // }
    //
    // @PostMapping("/ticket")
    // public Ticket postTicket(@RequestBody Ticket ticket) {
    // return ticketRepository.insert(ticket);
    // }

    @GetMapping("/ticket")
    public Optional<Ticket> getTicket(TicketId id) {
        return customerService.find(id);
    }

    @PostMapping("/ticket")
    public Ticket postTicket(@RequestBody Ticket ticket) {
        return customerService.insert(ticket);
    }

    @PutMapping("/ticket")
    public Ticket putTicket(@RequestBody Ticket ticket) {
        return customerService.save(ticket);
    }

    @GetMapping("/{id}")
    public Optional<Customer> getCustomer(@PathVariable String id) {
        return customerService.findCustomerById(id);
    }

    @GetMapping("/{storeId}/{number}")
    public Optional<Customer> getCustomerByIndex(@PathVariable Long storeId, @PathVariable Long number) {
        return customerService.findCustomerByIndex(storeId, number);
    }

    @PostMapping
    public Customer postCustomer(@RequestBody Customer customer) {
        return customerService.insert(customer);
    }

    @PutMapping
    public Customer putCustomer(@RequestBody Customer customer) {
        return customerService.save(customer);
    }

    @PostMapping("/sale")
    public Sale postSale(@RequestBody Sale sale) {
        return customerService.insert(sale);
    }

    @GetMapping("/sale/{id}")
    public Optional<Sale> getSale(@PathVariable String id) {
        return customerService.findSaleById(id);
    }

    @GetMapping("/sale")
    public Optional<Sale> getSale(TicketId ticketId) {
        return customerService.findSaleByTicketId(ticketId);
    }
}
