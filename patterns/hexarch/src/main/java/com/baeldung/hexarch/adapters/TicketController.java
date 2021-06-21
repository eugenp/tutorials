package com.baeldung.hexarch.adapters;

import com.baeldung.hexarch.domain.Ticket;
import com.baeldung.hexarch.service.TicketServicePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tickets/")
public class TicketController {

    @Autowired
    private TicketServicePort ticketService;

    @PostMapping("create")
    public String createTicket(@RequestBody Ticket ticket) {
        return ticketService.createTicket(ticket);
    }

    @GetMapping("/{ticketId}")
    @ResponseBody
    public Ticket getTicketDetails(@PathVariable String ticketId) {
        return ticketService.retrieveTicket(ticketId);
    }

    @DeleteMapping("/{ticketId}")
    @ResponseBody
    public String cancelTicket(@PathVariable String ticketId) {
        if (ticketService.cancelTicket(ticketId)) {
            return "Ticket Cancelled";
        } else {
            return "Ticket Not Found";
        }
    }

}
