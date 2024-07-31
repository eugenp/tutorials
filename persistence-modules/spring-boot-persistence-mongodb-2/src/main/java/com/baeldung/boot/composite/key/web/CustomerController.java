package com.baeldung.boot.composite.key.web;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.boot.composite.key.data.Ticket;
import com.baeldung.boot.composite.key.data.TicketId;
import com.baeldung.boot.composite.key.service.CustomerService;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

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
}
