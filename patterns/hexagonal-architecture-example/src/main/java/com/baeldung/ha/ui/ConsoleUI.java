package com.baeldung.ha.ui;

import com.baeldung.ha.application.ImmuneService;
import com.baeldung.ha.application.ImmuneServiceImpl;
import com.baeldung.ha.application.ImmuneStore;
import com.baeldung.ha.domain.Antibody;
import com.baeldung.ha.domain.Antigen;
import com.baeldung.ha.domain.InvalidAntigenException;
import com.baeldung.ha.domain.LymphaticSystem;
import com.baeldung.ha.infraestructure.MemoryImmuneStore;

import java.util.Scanner;

public class ConsoleUI {
    static ImmuneStore store = new MemoryImmuneStore();
    static ImmuneService service = new ImmuneServiceImpl(store);
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        final String messageFormat = "\nEnter antigen value (%d to %d) or '0' to exit: ";
        int value = 0;
        do {
            System.out.print(String.format(messageFormat, 1, LymphaticSystem.MAX_ANTIGEN_VALUE - 1));
            try {
                value = scanner.nextInt();
                Antibody antibody = service.respond(new Antigen(value));
                System.out.println(antibody);
            } catch (InvalidAntigenException e) {
                System.out.println("Invalid antigen value: " + e.getValue());
            } catch (Exception e) {
                System.out.println("Antigen value must be an integer");
            }
        } while (value != 0);
    }
}
