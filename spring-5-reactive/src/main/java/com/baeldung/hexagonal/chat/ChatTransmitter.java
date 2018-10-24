package com.baeldung.hexagonal.chat;

interface ChatTransmitter {
    void transmit(ChatMessage message);
}
