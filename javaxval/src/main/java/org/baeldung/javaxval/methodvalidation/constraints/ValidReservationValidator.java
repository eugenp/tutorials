package org.baeldung.javaxval.methodvalidation.constraints;

import org.baeldung.javaxval.methodvalidation.model.Reservation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class ValidReservationValidator implements ConstraintValidator<ValidReservation, Reservation> {

    @Override
    public boolean isValid(Reservation reservation, ConstraintValidatorContext context) {

        if (reservation == null) {
            return true;
        }

        if (!(reservation instanceof Reservation)) {
            throw new IllegalArgumentException("Illegal method signature, expected parameter of type Reservation.");
        }

        if (reservation.getBegin() == null || reservation.getEnd() == null || reservation.getCustomer() == null) {
            return false;
        }

        return (reservation.getBegin()
                .isAfter(LocalDate.now())
                && reservation.getBegin()
                .isBefore(reservation.getEnd())
                && reservation.getRoom() > 0);
    }
}
