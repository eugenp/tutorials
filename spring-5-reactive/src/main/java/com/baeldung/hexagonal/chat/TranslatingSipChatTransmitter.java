package com.baeldung.hexagonal.chat;

class TranslatingSipChatTransmitter extends SipChatTransmitter {
    private String targetLanguage;

    public static final String German = "German";

    public TranslatingSipChatTransmitter(String targetLanguage) {
        this.targetLanguage = targetLanguage;
    }

    private void translate(ChatMessage message) {
        String text = message.getMessage();

        if (targetLanguage.equals(German))
            text = "ACHTUNG! " + text;

        message.setMessage(text);
    }

    public void transmit(ChatMessage message) {
        translate(message);

        super.transmit(message);
    }
}
