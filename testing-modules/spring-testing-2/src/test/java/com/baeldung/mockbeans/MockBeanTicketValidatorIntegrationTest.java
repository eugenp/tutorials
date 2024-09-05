package com.baeldung.mockbeans;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@MockBean(CustomerRepository.class)
@MockBean(TicketRepository.class)
@SpringBootTest(classes = Application.class)
class MockBeanTicketValidatorIntegrationTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private TicketValidator ticketValidator;

    @Test
    void givenUnknownCustomer_whenValidate_thenThrowException() {
        String code = UUID.randomUUID()
            .toString();
        when(customerRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> ticketValidator.validate(1L, code));
    }
}