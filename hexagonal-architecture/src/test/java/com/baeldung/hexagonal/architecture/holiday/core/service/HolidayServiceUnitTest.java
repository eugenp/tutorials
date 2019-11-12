package com.baeldung.hexagonal.architecture.holiday.core.service;

import com.baeldung.hexagonal.architecture.holiday.adapter.InMemoryEmployeeRepository;
import com.baeldung.hexagonal.architecture.holiday.adapter.InMemoryHolidayRepository;
import com.baeldung.hexagonal.architecture.holiday.port.EmployeeRepository;
import com.baeldung.hexagonal.architecture.holiday.port.HolidayRepository;
import com.baeldung.hexagonal.architecture.holiday.port.Notifier;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

class HolidayServiceUnitTest {

    private HolidayService holidayService;

    public HolidayServiceUnitTest() {

        EmployeeRepository employeeRepository = new InMemoryEmployeeRepository();
        HolidayRepository holidayRepository = new InMemoryHolidayRepository();
        Notifier notifierMock = (recipient, message) -> {
            // nothing to do
        };

        this.holidayService = new HolidayServiceImpl(employeeRepository, holidayRepository, notifierMock);
    }

    @Test
    @DisplayName("The holiday application has been sent")
    void submitHolidayApplication() {
        UUID employeeId = UUID.randomUUID();
        UUID requestId = holidayService.submitHolidayApplication(employeeId, LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2), "type");

        Assertions.assertThat(requestId).isNotNull();
        // more assertion should goes here ...
    }

    @Test
    @DisplayName("The holiday application has been accepted")
    void acceptVacation() {
        holidayService.acceptHoliday(UUID.randomUUID());

        // assertion should goes here
    }

    @Test
    @DisplayName("The holiday application has been rejected")
    void rejectHoliday() {
        holidayService.rejectHoliday(UUID.randomUUID(), "rejection reason");

        // assertion should goes here
    }
}