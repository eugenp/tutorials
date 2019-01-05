package com.baeldung.hexagonalarchapp.application;

import java.text.SimpleDateFormat;
import java.util.Scanner;

import com.baeldung.hexagonalarchapp.domain.ISuspectHandler;
import com.baeldung.hexagonalarchapp.domain.PersonRecord;

public class SuspectDetailsFromConsole implements ISuspectHandler {

    @Override
    public PersonRecord getSuspectDetails() {
        PersonRecord suspectRecord = new PersonRecord();
        try {
            Scanner scanner = new Scanner(System.in);

            System.out.println("Enter First Name: ");
            suspectRecord.setFirstName(scanner.nextLine());

            System.out.println("Enter Last Name: ");
            suspectRecord.setLastName(scanner.nextLine());

            System.out.println("Enter Gender (M/F): ");
            suspectRecord.setGender(scanner.nextLine());

            System.out.println("Enter SSN (XXX-XX-XXXX): ");
            suspectRecord.setSsn(scanner.nextLine());

            System.out.println("Enter Date Of Birth MM/DD/YYYY : ");
            suspectRecord.setDateOfBirth(new SimpleDateFormat("MM/dd/yyyy").parse(scanner.nextLine()));
            
            scanner.close();
        } catch (Exception ex) {
            // Error while getting suspect details
        }

        return suspectRecord;
    }

}
