package com.baeldung.javaxval.methodvalidation;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.ConstraintViolationException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.baeldung.javaxval.methodvalidation.model.Customer;
import com.baeldung.javaxval.methodvalidation.model.Reservation;
import com.baeldung.javaxval.methodvalidation.model.ReservationManagement;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { MethodValidationConfig.class }, loader = AnnotationConfigContextLoader.class)
public class ContainerValidationIntegrationTest {

    @Autowired
    ReservationManagement reservationManagement;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void whenValidationWithInvalidMethodParameters_thenConstraintViolationException() {

        exception.expect(ConstraintViolationException.class);
        reservationManagement.createReservation(LocalDate.now(), 0, null);
    }

    @Test
    public void whenValidationWithValidMethodParameters_thenNoException() {

        reservationManagement.createReservation(LocalDate.now()
            .plusDays(1), 1, new Customer("William", "Smith"));
    }

    @Test
    public void whenCrossParameterValidationWithInvalidParameters_thenConstraintViolationException() {

        exception.expect(ConstraintViolationException.class);
        reservationManagement.createReservation(LocalDate.now(), LocalDate.now(), null);
    }

    @Test
    public void whenCrossParameterValidationWithValidParameters_thenNoException() {

        reservationManagement.createReservation(LocalDate.now()
            .plusDays(1),
            LocalDate.now()
                .plusDays(2),
            new Customer("William", "Smith"));
    }

    @Test
    public void whenValidationWithInvalidReturnValue_thenConstraintViolationException() {

        exception.expect(ConstraintViolationException.class);
        List<Customer> list = reservationManagement.getAllCustomers();
    }

    @Test
    public void whenValidationWithInvalidCascadedValue_thenConstraintViolationException() {

        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        Reservation reservation = new Reservation(LocalDate.now()
            .plusDays(1),
            LocalDate.now()
                .plusDays(2),
            customer, 1);

        exception.expect(ConstraintViolationException.class);
        reservationManagement.createReservation(reservation);
    }

    @Test
    public void whenValidationWithValidCascadedValue_thenCNoException() {

        Customer customer = new Customer();
        customer.setFirstName("William");
        customer.setLastName("Smith");
        Reservation reservation = new Reservation(LocalDate.now()
            .plusDays(1),
            LocalDate.now()
                .plusDays(2),
            customer, 1);

        reservationManagement.createReservation(reservation);
    }
}
