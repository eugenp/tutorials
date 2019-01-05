package com.baeldung.hexagonalarchapp.application;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Scanner;

import com.baeldung.hexagonalarchapp.domain.ISuspectHandler;
import com.baeldung.hexagonalarchapp.domain.PersonRecord;

public class SuspectDetailsFromFile implements ISuspectHandler {

    @Override
    public PersonRecord getSuspectDetails() {
        PersonRecord suspectRecord = new PersonRecord();
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource("suspect-data.csv")
                .getFile());

            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] columns = line.split(",");
                //Check if all values have been read 
                if (columns.length == 5) {
                    suspectRecord.setFirstName(columns[0]);
                    suspectRecord.setLastName(columns[1]);
                    suspectRecord.setGender(columns[2]);
                    suspectRecord.setSsn(columns[3]);
                    suspectRecord.setDateOfBirth(new SimpleDateFormat("MM/dd/yyyy").parse(columns[4]));
                }
            }
            scanner.close();

        } catch (Exception ex) {
            // Error while getting suspect details
        }

        return suspectRecord;
    }

}
