package com.baeldung.hexagonal.domain;

import java.time.LocalDateTime;

public class ChatMessage {
    private LocalDateTime timeSent;
    private ChatUser from;
    private ChatUser to;
    private String contents;

    public LocalDateTime getTimeSent() {
        return timeSent;
    }

    public ChatUser getFrom() {
        return from;
    }

    public ChatUser getTo() {
        return to;
    }

    public String getContents() {
        return contents;
    }

    public ChatMessage(LocalDateTime timeSent, ChatUser from, ChatUser to, String contents) {
        this.timeSent = timeSent;
        this.from = from;
        this.to = to;
        this.contents = contents;
    }

}
