package org.baeldung.javaxval.methodvalidation.model;

import org.baeldung.javaxval.methodvalidation.constraints.ConsistentDateParameters;
import org.baeldung.javaxval.methodvalidation.constraints.ValidReservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

@Controller
@Validated
public class ReservationManagement {

    @Autowired
    private ApplicationContext applicationContext;

    @ConsistentDateParameters
    public void createReservation(LocalDate begin, LocalDate end, @NotNull Customer customer) {

        // ...
    }

    public void createReservation(@NotNull @Future LocalDate begin, @Min(1) int duration, @NotNull Customer customer) {

        // ...
    }

    public void createReservation(@Valid Reservation reservation) {

        // ...
    }

    @NotNull
    @Size(min = 1)
    public List<@NotNull Customer> getAllCustomers() {

        return null;
    }

    @Valid
    public Reservation getReservationById(int id) {

        return null;
    }
}
