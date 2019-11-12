package com.baeldung.hexagonal.architecture.holiday.port;

import java.util.Scanner;

public interface UIPort {

    void submitVacationRequest(Scanner scanner);

    void acceptVacation(Scanner scanner);

    void rejectVacation(Scanner scanner);
}
