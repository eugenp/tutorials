package com.baeldung.subclassinnerclass;

public class NotificationService {
    void notifyMessages() {
        // Sending a Text Message
        Message textMessage = new Message();
        Notifier textNotifier = new TextMessageNotifier();

        textNotifier.notify(textMessage);

        // Sending an Email Message
        Message emailMessage = new Message();
        Notifier emailNotifier = new EmailNotifier();

        emailNotifier.notify(emailMessage);
    }
}
