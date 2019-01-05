package com.baeldung.hexagonalarchapp.application;

import com.baeldung.hexagonalarchapp.domain.CriminalHistoryChecker;
import com.baeldung.hexagonalarchapp.domain.ICriminalDataHandler;
import com.baeldung.hexagonalarchapp.domain.ISuspectHandler;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        
        // Scenario 1 - Infrastructure for input is console
        {
            ISuspectHandler suspectHandlerConsole = new SuspectDetailsFromConsole();
            ICriminalDataHandler criminalData = new CriminalDataFetcher();

            CriminalHistoryChecker criminalHistoryChecker = new CriminalHistoryChecker(suspectHandlerConsole, criminalData);
            boolean isSuspectCriminal = criminalHistoryChecker.isSuspectACriminal();

            System.out.println("Suspect with details entered has criminal history? " + isSuspectCriminal);

        }

        // Scenario 2 - Infrastructure for input is file
        {
            ISuspectHandler suspectHandlerFile = new SuspectDetailsFromFile();
            ICriminalDataHandler criminalData = new CriminalDataFetcher();

            CriminalHistoryChecker criminalHistoryChecker = new CriminalHistoryChecker(suspectHandlerFile, criminalData);
            boolean isSuspectCriminal = criminalHistoryChecker.isSuspectACriminal();

            System.out.println("Suspect from file has criminal history? " + isSuspectCriminal);
        }

    }
}
