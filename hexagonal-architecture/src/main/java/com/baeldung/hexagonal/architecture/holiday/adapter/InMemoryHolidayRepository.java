package com.baeldung.hexagonal.architecture.holiday.adapter;

import com.baeldung.hexagonal.architecture.holiday.core.domain.Holiday;
import com.baeldung.hexagonal.architecture.holiday.core.domain.HolidayPlan;
import com.baeldung.hexagonal.architecture.holiday.port.HolidayRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
public class InMemoryHolidayRepository implements HolidayRepository {

    private final ConcurrentHashMap<UUID, HolidayPlan> holidays = new ConcurrentHashMap<>();

    @Override
    public Optional<HolidayPlan> getInformationAboutHolidays(UUID employeeId) {
        return Optional.ofNullable(holidays.get(employeeId));
    }

    @Override
    public void save(Holiday holiday) {
        // save operation goes here
    }
}
