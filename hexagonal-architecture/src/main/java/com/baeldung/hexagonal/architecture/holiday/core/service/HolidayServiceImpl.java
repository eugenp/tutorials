package com.baeldung.hexagonal.architecture.holiday.core.service;

import com.baeldung.hexagonal.architecture.holiday.core.domain.ContractType;
import com.baeldung.hexagonal.architecture.holiday.core.domain.Employee;
import com.baeldung.hexagonal.architecture.holiday.core.domain.Holiday;
import com.baeldung.hexagonal.architecture.holiday.core.domain.HolidayPlan;
import com.baeldung.hexagonal.architecture.holiday.core.exception.HolidayNotAccepted;
import com.baeldung.hexagonal.architecture.holiday.port.EmployeeRepository;
import com.baeldung.hexagonal.architecture.holiday.port.HolidayRepository;
import com.baeldung.hexagonal.architecture.holiday.port.Notifier;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class HolidayServiceImpl implements HolidayService {

    private final EmployeeRepository employeeRepository;
    private final HolidayRepository holidayRepository;
    private final Notifier notifier;

    @Override
    public UUID submitHolidayApplication(UUID employeeId, LocalDateTime from, LocalDateTime to, String type) {
        Optional<Employee> optionalEmployee = employeeRepository.getInformationAboutEmployee(employeeId);
        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();
            if (ContractType.CONTRACT_OF_EMPLOYMENT != employee.getContractType()) {
                throw new HolidayNotAccepted("Holiday is not accepted for " + employee.getContractType());
            }

            Optional<HolidayPlan> holidayPlan = holidayRepository.getInformationAboutHolidays(employeeId);
            // check if the employee can apply for vacation for the requested criteria
            // if NO then throw dedicated Exception
            // if YES then save information about that and notify everyone who should know about that
        }
        return UUID.randomUUID();
    }

    @Override
    public void acceptHoliday(UUID applicationId) {
        holidayRepository.save(Holiday.builder().id(applicationId).build());
        notifier.sendMessage(getRecipientFromContext(), getAcceptMessageFromConfiguration());
    }

    @Override
    public void rejectHoliday(UUID applicationId, String reason) {
        holidayRepository.save(Holiday.builder().id(applicationId).build());
        notifier.sendMessage(getRecipientFromContext(), getRejectMessageFromConfiguration(reason));
    }

    private String getRecipientFromContext() {
        return "recipient";
    }

    private String getAcceptMessageFromConfiguration() {
        return "accepted";
    }

    private String getRejectMessageFromConfiguration(String reason) {
        return "rejected: " + reason;
    }
}
