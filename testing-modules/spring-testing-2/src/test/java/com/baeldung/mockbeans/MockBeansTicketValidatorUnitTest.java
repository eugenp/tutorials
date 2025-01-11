package com.baeldung.mockbeans;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;

@MockBeans({ @MockBean(CustomerRepository.class), @MockBean(TicketRepository.class) })
@SpringBootTest(classes = Application.class)
class MockBeansTicketValidatorUnitTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private TicketValidator ticketValidator;

    @Test
    void givenCustomerAndTicket_whenValidate_thenReturnTrue() {
        String code = UUID.randomUUID()
            .toString();
        when(customerRepository.findById(any())).thenReturn(Optional.of(new Customer(1L, "John", "Doe")));
        when(ticketRepository.findByCode(any())).thenReturn(Optional.of(new Ticket()));

        assertTrue(ticketValidator.validate(1L, code));
    }
}