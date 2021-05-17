package com.baeldung.downloadAttachments;

import static org.junit.Assert.fail;
import org.junit.Test;

public class DownloadEmailAttachmentsUnitTest {
    @Test
    public void when_Run_then_downloadAttachments() {

        String host = "pop.gmail.com";
        String port = "995";
        String userName = "your_email";
        String password = "your_password";

        String saveDirectory = "valid_folder_path";

        DownloadEmailAttachments receiver = new DownloadEmailAttachments();
        receiver.setSaveDirectory(saveDirectory);
        try {
            receiver.downloadEmailAttachments(host, port, userName, password);
        } catch (Exception ex) {
            fail("Exception: " + ex);
        }
    }
}
