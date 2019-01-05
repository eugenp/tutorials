package com.baeldung.hexagonalarchapp.domain;

import java.util.List;

public interface ICriminalDataHandler {

    public List<PersonRecord> searchByName(String firstName, String lastName);

    public List<PersonRecord> searchBySsn(String ssn);

}
