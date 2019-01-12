package com.baeldung.hexagonalarchapp.application;

import com.baeldung.hexagonalarchapp.domain.CriminalHistoryChecker;
import com.baeldung.hexagonalarchapp.domain.ICriminalDataProvider;
import com.baeldung.hexagonalarchapp.domain.ISuspectDataProvider;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        
        // Scenario 1 - Infrastructure for input is console
        {
            ISuspectDataProvider suspectDataProviderFromConsole = new SuspectDetailsFromConsole();
            ICriminalDataProvider criminalDataFetcherFromDb = new CriminalDataFetcherFromDb();

            CriminalHistoryChecker criminalHistoryChecker = new CriminalHistoryChecker(suspectDataProviderFromConsole, criminalDataFetcherFromDb);
            boolean isSuspectCriminal = criminalHistoryChecker.isSuspectACriminal();

            System.out.println("Suspect with details entered has criminal history? " + isSuspectCriminal);

        }

        // Scenario 2 - Infrastructure for input is file
        {
            ISuspectDataProvider suspectDataProviderFromFile = new SuspectDetailsFromFile();
            ICriminalDataProvider criminalDataFetcherFromDb = new CriminalDataFetcherFromDb();

            CriminalHistoryChecker criminalHistoryChecker = new CriminalHistoryChecker(suspectDataProviderFromFile, criminalDataFetcherFromDb);
            boolean isSuspectCriminal = criminalHistoryChecker.isSuspectACriminal();

            System.out.println("Suspect from file has criminal history? " + isSuspectCriminal);
        }

    }
}
