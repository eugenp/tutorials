package com.baeldung.hexagonalarchapp.domain;

import java.util.List;

public class CriminalHistoryChecker {

    private ISuspectDataProvider suspectDataProvider;
    private ICriminalDataProvider criminalDataProvider;

    // Dependency injection via constructor
    public CriminalHistoryChecker(ISuspectDataProvider suspectDataProvider, ICriminalDataProvider criminalDataProvider) {
        this.suspectDataProvider = suspectDataProvider;
        this.criminalDataProvider = criminalDataProvider;
    }

    public boolean isSuspectACriminal() {
        // Assume suspect is not a criminal
        boolean isSuspectACriminal = false;

        // Get suspect details using suspect interface
        PersonRecord suspectRecord = suspectDataProvider.getSuspectDetails();
        // Search suspect in criminal DB by SSN using data handler interface
        List<PersonRecord> criminalSearchResultList = criminalDataProvider.searchBySsn(suspectRecord.getSsn());

        // Search suspect in criminal DB by first name and last name using data handler interface
        if (criminalSearchResultList == null || criminalSearchResultList.size() == 0) {
            criminalSearchResultList = criminalDataProvider.searchByName(suspectRecord.getFirstName(), suspectRecord.getLastName());
        }

        if (criminalSearchResultList != null && criminalSearchResultList.size() > 0) {
            isSuspectACriminal = true;
        }

        return isSuspectACriminal;
    }

}
