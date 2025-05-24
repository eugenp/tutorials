package com.baeldung.anonymousclass;

public class SmsSenderService implements SenderService {

    @Override
    public String callSender(Sender sender) {
        return sender.send("SMS Notification");
    }

}
