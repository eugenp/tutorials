package com.baeldung.hexagonal.architecture.holiday.port;

import com.baeldung.hexagonal.architecture.holiday.core.domain.Holiday;
import com.baeldung.hexagonal.architecture.holiday.core.domain.HolidayPlan;

import java.util.Optional;
import java.util.UUID;

public interface HolidayRepository {

    Optional<HolidayPlan> getInformationAboutHolidays(UUID employeeId);

    void save(Holiday holiday);
}
