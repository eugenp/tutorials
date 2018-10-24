package com.baeldung.hexagonal.chat;

class SipChatTransmitter implements ChatTransmitter {
    @Override
    public void transmit(ChatMessage message) {
        // Transmit message using the Session Initiation Protocol (SIP)
    }
}
