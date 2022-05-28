package com.baeldung.boot.composite.key;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.util.Optional;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.boot.composite.key.data.Customer;
import com.baeldung.boot.composite.key.data.Ticket;
import com.baeldung.boot.composite.key.data.TicketId;
import com.baeldung.boot.composite.key.service.CustomerService;

@SpringBootTest
@DirtiesContext
@RunWith(SpringRunner.class)
public class CustomerServiceIntegrationTest {
    @Autowired
    private CustomerService service;

    private static Ticket ticket;
    private static TicketId ticketId;

    @BeforeClass
    public static void setup() {
        ticket = new Ticket();
        ticket.setEvent("Event A");

        ticketId = new TicketId();
        ticketId.setDate("2020-01-01");
        ticketId.setVenue("Venue A");
        ticket.setId(ticketId);
    }

    @Test
    public void givenCompositeId_whenObjectSaved_thenIdMatches() {
        Ticket savedTicket = service.insert(ticket);
        assertEquals(savedTicket.getId(), ticket.getId());
    }

    @Test
    public void givenCompositeId_whenSearchingByIdObject_thenFound() {
        Optional<Ticket> optionalTicket = service.find(ticketId);

        assertThat(optionalTicket.isPresent());
        Ticket savedTicket = optionalTicket.get();

        assertEquals(savedTicket.getId(), ticketId);
    }

    @Test
    public void givenCompoundUniqueIndex_whenSearchingByGeneratedId_thenFound() {
        Customer customer = new Customer();
        customer.setName("Name");
        customer.setNumber(0l);
        customer.setStoreId(0l);

        Customer savedCustomer = service.insert(customer);

        Optional<Customer> optional = service.findCustomerById(savedCustomer.getId());

        assertThat(optional.isPresent());
    }

    @Test
    public void givenCompositeId_whenDupeInsert_thenExceptionIsThrown() {
        Ticket ticket = new Ticket();
        ticket.setEvent("C");

        TicketId ticketId = new TicketId();
        ticketId.setDate("2020-01-01");
        ticketId.setVenue("V");
        ticket.setId(ticketId);

        assertThrows(DuplicateKeyException.class, () -> {
            service.insert(ticket);
            service.insert(ticket);
        });
    }

    @Test
    public void givenCompositeId_whenDupeSave_thenObjectUpdated() {
        TicketId ticketId = new TicketId();
        ticketId.setDate("2020-01-01");
        ticketId.setVenue("Venue");

        Ticket ticketA = new Ticket();
        ticketA.setEvent("A");
        ticketA.setId(ticketId);

        service.save(ticketA);

        Ticket ticketB = new Ticket();
        ticketB.setEvent("B");
        ticketB.setId(ticketId);

        Ticket savedTicket = service.save(ticketB);
        assertEquals(savedTicket.getEvent(), ticketB.getEvent());
    }

    @Test
    public void givenCompoundUniqueIndex_whenDupeInsert_thenExceptionIsThrown() {
        Customer customer = new Customer();
        customer.setName("Name");
        customer.setNumber(1l);
        customer.setStoreId(2l);

        assertThrows(DuplicateKeyException.class, () -> {
            service.insert(customer);
            service.insert(customer);
        });
    }

    @Test
    public void givenCompoundUniqueIndex_whenDupeSave_thenExceptionIsThrown() {
        Customer customerA = new Customer();
        customerA.setName("Name A");
        customerA.setNumber(1l);
        customerA.setStoreId(2l);

        Customer customerB = new Customer();
        customerB.setName("Name B");
        customerB.setNumber(1l);
        customerB.setStoreId(2l);

        assertThrows(DuplicateKeyException.class, () -> {
            service.save(customerA);
            service.save(customerB);
        });
    }
}
