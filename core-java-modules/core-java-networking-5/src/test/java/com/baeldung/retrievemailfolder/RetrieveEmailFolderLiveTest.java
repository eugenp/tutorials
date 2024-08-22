package com.baeldung.retrievemailfolder;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

import java.util.List;

public class RetrieveEmailFolderLiveTest {

    @Test
    public void givenEmail_whenUsingIMAP_thenRetrieveEmailFolder() throws Exception {
        RetrieveEmailFolder retrieveEmailFolder = new RetrieveEmailFolder();
        List<String> availableFolders = retrieveEmailFolder.connectToMailServer("imap.gmail.com", "test@gmail.com", "password");
        for (String availableFolder : availableFolders) {
            System.out.println(availableFolder);
        }
        assertTrue(availableFolders.contains("INBOX"));
        assertTrue(availableFolders.contains("Spam"));
    }
}

