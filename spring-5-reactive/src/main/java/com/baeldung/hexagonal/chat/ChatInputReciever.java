package com.baeldung.hexagonal.chat;

import java.util.List;

interface ChatInputReciever {
    void onChatMessageInput(String message, List<String> recipients);
}
