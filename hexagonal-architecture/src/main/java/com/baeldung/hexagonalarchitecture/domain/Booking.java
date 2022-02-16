package com.baeldung.hexagonalarchitecture.domain;

import java.time.LocalDate;
import java.time.Period;
import java.util.UUID;

public class Booking {
    private UUID uuid;
    private FullName bookingName;
    private PeriodDetails periodDetails;

    public Booking(UUID uuid, FullName bookingName, PeriodDetails periodDetails) {
        this.uuid = uuid;
        this.bookingName = bookingName;
        this.periodDetails = periodDetails;
    }

    public Booking(FullName bookingName, PeriodDetails periodDetails) {
        this(UUID.randomUUID(), bookingName, periodDetails);
    }

    public UUID getUuid() {
        return uuid;
    }

    public FullName getBookingName() {
        return bookingName;
    }

    public PeriodDetails getPeriodDetails() {
        return periodDetails;
    }

    public static class PeriodDetails {
        private LocalDate startDate;
        private LocalDate endDate;

        public PeriodDetails(LocalDate startDate, LocalDate endDate) {
            this.startDate = startDate;
            this.endDate = endDate;
        }

        public int getPeriodInDays() {
            return Period.between(startDate, endDate)
                .getDays();
        }

        public LocalDate getStartDate() {
            return startDate;
        }

        public LocalDate getEndDate() {
            return endDate;
        }
    }

    public static class FullName {
        private String firstName;
        private String lastName;

        public FullName(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public String get() {
            return firstName + " " + lastName.toUpperCase();
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }
    }
}
