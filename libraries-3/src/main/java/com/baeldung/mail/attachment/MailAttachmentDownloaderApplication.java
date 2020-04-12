package com.baeldung.mail.attachment;

public class MailAttachmentDownloaderApplication {
    public static void main(String[] args) {
        // for POP3
        String protocol = "pop3";
        String host = "pop.gmail.com";
        String port = "995";

        // for IMAP
        // String protocol = "imap";
        // String host = "imap.gmail.com";
        // String port = "993";

        String userName = "your_email_address";
        String password = "your_email_password";

        String saveDirectory = "directoryPath";

        EmailAttachmentReceiver attachmentReceiver = new EmailAttachmentReceiver(host, protocol, port, userName, password);
        attachmentReceiver.downloadEmailAttachmentsTo(saveDirectory);
    }
}
