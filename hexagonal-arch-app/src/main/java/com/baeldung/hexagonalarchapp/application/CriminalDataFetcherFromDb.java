package com.baeldung.hexagonalarchapp.application;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.baeldung.hexagonalarchapp.domain.ICriminalDataProvider;
import com.baeldung.hexagonalarchapp.domain.PersonRecord;

public class CriminalDataFetcherFromDb implements ICriminalDataProvider {

    private List<PersonRecord> dbDataList;

    public CriminalDataFetcherFromDb() {
        dbDataList = getInMemoryDb();
    }

    @Override
    public List<PersonRecord> searchByName(String firstName, String lastName) {
        List<PersonRecord> searchResult = dbDataList.stream()
            .filter(p -> p.getFirstName()
                .equalsIgnoreCase(firstName)
                && p.getLastName()
                    .equalsIgnoreCase(lastName))
            .collect(Collectors.toList());
        return searchResult;

    }

    @Override
    public List<PersonRecord> searchBySsn(String ssn) {
        List<PersonRecord> searchResult = dbDataList.stream()
            .filter(p -> p.getSsn()
                .equals(ssn))
            .collect(Collectors.toList());
        return searchResult;
    }

    private List<PersonRecord> getInMemoryDb() {
        List<PersonRecord> dbList = new ArrayList<>();
        String[] randomFirstName = { "John", "Pam", "Alisha", "Nick", "Harry" };
        String[] randomLasttName = { "Smith", "Campbell", "Jones", "Darwin", "Potter" };
        String[] randomGender = { "M", "F", "F", "M", "M" };
        String[] randomDateOfBirth = { "02/03/1990", "03/01/1992", "05/09/1974", "12/12/1967", "07/04/1997" };
        String[] randomSsn = { "123-22-3456", "144-44-5546", "129-35-1231", "111-12-9956", "554-33-9981" };

        for (int i = 0; i < 5; i++) {
            PersonRecord personRecord = new PersonRecord();
            personRecord.setFirstName(randomFirstName[i]);
            personRecord.setLastName(randomLasttName[i]);
            personRecord.setGender(randomGender[i]);
            try {
                personRecord.setDateOfBirth(new SimpleDateFormat("MM/dd/yyyy").parse(randomDateOfBirth[i]));
            } catch (Exception ex) {
                // Error while setting data of birth
            }
            personRecord.setSsn(randomSsn[i]);
            dbList.add(personRecord);
        }

        return dbList;
    }

}
