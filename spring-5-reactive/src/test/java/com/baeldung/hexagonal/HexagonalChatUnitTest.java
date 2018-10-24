package com.baeldung.hexagonal.chat;

import org.junit.BeforeClass;
import org.junit.Test;

public class HexagonalChatUnitTest {    
    @Test
    public void transmit_random_chat_thenSuccess() throws Exception {
        ChatTransmitter chatTransmitPort = new TestChatTransmitter();
        ChatLogic chatLogic = new ChatLogic(chatTransmitPort);        
        TestChatInput testChatInputAdapter = new TestChatInput(chatLogic);
        
        testChatInputAdapter.injectChat();
    }
}
