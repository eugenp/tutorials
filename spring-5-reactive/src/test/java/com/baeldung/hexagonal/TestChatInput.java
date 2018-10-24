package com.baeldung.hexagonal.chat;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

class TestChatInput {
    private ChatInputReciever chatInputReciver;
    
    public TestChatInput(ChatInputReciever chatInputReciver) {
        this.chatInputReciver = chatInputReciver;
    }
    
    public void injectChat() {
        List<String> recipients = new ArrayList(Arrays.asList("tom", "dick", "harry"));
        this.chatInputReciver.onChatMessageInput("Hello there!", recipients);
    }
}
