package com.baeldung.mail.attachment;

import com.baeldung.mail.attachment.utils.EmailAttachmentReceiver;

public class Application {

    public static void main(String[] args) throws Exception {
        String userName = "userName";
        String password = "password";
        String saveDirectory = "directoryPath";
        EmailAttachmentReceiver attachmentReceiver = new EmailAttachmentReceiver(userName, password);
        attachmentReceiver.downloadEmailAttachmentsTo(saveDirectory);
    }
}
