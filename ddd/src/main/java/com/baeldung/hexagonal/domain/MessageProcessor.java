package com.baeldung.hexagonal.domain;

import com.baeldung.hexagonal.port.MessageRequestService;
import com.baeldung.hexagonal.port.SendMessageService;

public class MessageProcessor {

    private MessageRequestService messageRequestService;
    private SendMessageService sendMessageService;

    public void registerRequestService(MessageRequestService messageRequestService) {
        this.messageRequestService = messageRequestService;
    }

    public void registerSendService(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }

    public void process() {
        while(true) {
            MessageRequest messageRequest = messageRequestService.receiveRequest();
            if (messageRequest != null) {
                sendMessageService.sendMessage(messageRequest);
            }
        }
    }
}
