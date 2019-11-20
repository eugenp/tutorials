package com.baeldung.hexagonal.application;

import com.baeldung.hexagonal.domain.*;

import java.time.LocalDateTime;

public class ChatManager implements ISendMessage {

    // The domain doesn't need to know where messages will be stored
    private IStoreMessages messageStore;

    // The domain doesn't need to know how messages will be displayed
    private IDisplayMessages messageDisplayer;

    public ChatManager(IStoreMessages messageStore, IDisplayMessages displayMessages) {
        this.messageStore = messageStore;
        this.messageDisplayer = displayMessages;
    }

    @Override
    public void sendMessage(ChatUser from, ChatUser to, String message) {
        ChatMessage chatMessage = new ChatMessage(LocalDateTime.now(), from, to, message);
        this.messageStore.storeMessage(chatMessage);
        this.messageDisplayer.displayMessages();
    }

}
