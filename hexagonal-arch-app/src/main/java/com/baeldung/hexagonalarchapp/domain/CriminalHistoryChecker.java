package com.baeldung.hexagonalarchapp.domain;

import java.util.List;

public class CriminalHistoryChecker {

    private ISuspectHandler suspectHandler;
    private ICriminalDataHandler criminalDataHandler;

    // Dependency injection via constructor
    public CriminalHistoryChecker(ISuspectHandler suspectHandler, ICriminalDataHandler criminalDataHandler) {
        this.suspectHandler = suspectHandler;
        this.criminalDataHandler = criminalDataHandler;
    }

    public boolean isSuspectACriminal() {
        //Assume suspect is not a criminal
        boolean isSuspectACriminal = false;

        // Get suspect details using suspect interface 
        PersonRecord suspect = suspectHandler.getSuspectDetails();
        // Search suspect in criminal DB by SSN using data handler interface
        List<PersonRecord> criminalList = criminalDataHandler.searchBySsn(suspect.getSsn());

        // Search suspect in criminal DB by first name and last name using data handler interface
        if (criminalList == null || criminalList.size() == 0) {
            criminalList = criminalDataHandler.searchByName(suspect.getFirstName(), suspect.getLastName());
        }

        if (criminalList != null && criminalList.size() > 0) {
            isSuspectACriminal = true;
        }

        return isSuspectACriminal;
    }

}
