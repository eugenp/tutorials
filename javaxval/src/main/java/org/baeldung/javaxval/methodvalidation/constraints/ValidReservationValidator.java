package org.baeldung.javaxval.methodvalidation.constraints;

import org.baeldung.javaxval.methodvalidation.model.Reservation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class ValidReservationValidator implements ConstraintValidator<ValidReservation, Reservation> {

    @Override
    public void initialize(ValidReservation constraintAnnotation) {
    }

    @Override
    public boolean isValid(Reservation reservation, ConstraintValidatorContext context) {

        if (reservation == null) {
            return false;
        }

        if (!(reservation instanceof Reservation)) {
            throw new IllegalArgumentException("Illegal method signature, expected parameter of type Reservation.");
        }

        if (reservation.getBegin() == null || reservation.getEnd() == null || reservation.getCustomer() == null) {
            return false;
        }

        if (reservation.getBegin()
            .isAfter(LocalDate.now())
            && reservation.getBegin()
                .isBefore(reservation.getEnd())
            && reservation.getRoom() > 0) {

            return true;
        }
        return false;
    }
}
