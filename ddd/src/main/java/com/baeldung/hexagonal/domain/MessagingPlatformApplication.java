package com.baeldung.hexagonal.domain;

import com.baeldung.hexagonal.adaptor.AirphoneSendMessageAdaptor;
import com.baeldung.hexagonal.adaptor.ChasingBankMessageRequestAdaptor;

public class MessagingPlatformApplication {
    public static void main(String[] args) {
        MessageProcessor messageProcessor = new MessageProcessor();
        messageProcessor.registerRequestService(new ChasingBankMessageRequestAdaptor());
        messageProcessor.registerSendService(new AirphoneSendMessageAdaptor());
        messageProcessor.process();
    }
}
