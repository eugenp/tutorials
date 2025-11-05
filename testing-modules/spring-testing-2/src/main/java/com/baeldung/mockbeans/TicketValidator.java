package com.baeldung.mockbeans;

import org.springframework.stereotype.Service;

@Service
public class TicketValidator {

    private final CustomerRepository customerRepository;

    private final TicketRepository ticketRepository;

    public TicketValidator(CustomerRepository customerRepository, TicketRepository ticketRepository) {
        this.customerRepository = customerRepository;
        this.ticketRepository = ticketRepository;
    }

    public boolean validate(Long customerId, String code) {
        customerRepository.findById(customerId)
            .orElseThrow(() -> new RuntimeException("Customer not found"));

        ticketRepository.findByCode(code)
            .orElseThrow(() -> new RuntimeException("Ticket with given code not found"));
        return true;
    }
}
