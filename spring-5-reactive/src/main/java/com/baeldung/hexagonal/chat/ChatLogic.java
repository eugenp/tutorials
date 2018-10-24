package com.baeldung.hexagonal.chat;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

class ChatLogic implements ChatInputReciever {
    private ChatTransmitter transmitter;

    public ChatLogic(ChatTransmitter transmitter) {
        this.setChatTransmitter(transmitter);
    }

    public void setChatTransmitter(ChatTransmitter transmitter) {
        this.transmitter = transmitter;
    }

    private void sanitizeMessage(ChatMessage chatMessage) {
        String text = chatMessage.getMessage();
        List<String> bannedWords = new ArrayList(Arrays.asList("barp", "bleep", "boop"));

        for (String word : bannedWords) {
            text = text.replace(word, "---");
        }

        chatMessage.setMessage(text);
    }

    @Override
    public void onChatMessageInput(String message, List<String> recipients) {
        ChatMessage chatMessage = new ChatMessage(message, recipients);

        sanitizeMessage(chatMessage);

        this.transmitter.transmit(chatMessage);
    }
}
