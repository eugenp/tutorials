package com.baeldung.hexagonal.chat;

import org.junit.BeforeClass;
import org.junit.Test;

public class HexagonalChatUnitTest {
    @Test
    public void transmitRandomChat_thenSuccess() throws Exception {
        ChatTransmitter chatTransmitPort = new TestChatTransmitter();
        ChatLogic chatLogic = new ChatLogic(chatTransmitPort);
        TestChatInput testChatInput = new TestChatInput(chatLogic);

        testChatInput.injectChat();
    }
}
