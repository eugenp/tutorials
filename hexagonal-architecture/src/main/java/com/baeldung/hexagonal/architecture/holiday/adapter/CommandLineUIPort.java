package com.baeldung.hexagonal.architecture.holiday.adapter;

import com.baeldung.hexagonal.architecture.holiday.core.service.HolidayService;
import com.baeldung.hexagonal.architecture.holiday.port.UIPort;

import java.util.Scanner;

public class CommandLineUIPort implements UIPort {

    private HolidayService holidayService;

    @Override
    public void submitVacationRequest(Scanner scanner) {
        String s = scanner.nextLine();
        // some logic put here
        System.out.println(holidayService.submitHolidayApplication(null, null, null, null));
    }

    @Override
    public void acceptVacation(Scanner scanner) {
        holidayService.acceptHoliday(null);
    }

    @Override
    public void rejectVacation(Scanner scanner) {
        holidayService.rejectHoliday(null, null);
    }
}
