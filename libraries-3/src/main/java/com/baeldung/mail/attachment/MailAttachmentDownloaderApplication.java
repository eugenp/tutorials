package com.baeldung.mail.attachment;

public class MailAttachmentDownloaderApplication {
    public static void main(String[] args) {
         String host = "imap.gmail.com";
         String port = "993";

        String userName = "your_email_address";
        String password = "your_email_password";

        String saveDirectory = "directoryPath";

        EmailAttachmentReceiver attachmentReceiver = new EmailAttachmentReceiver(host, port, userName, password);
        attachmentReceiver.downloadEmailAttachmentsTo(saveDirectory);
    }
}
