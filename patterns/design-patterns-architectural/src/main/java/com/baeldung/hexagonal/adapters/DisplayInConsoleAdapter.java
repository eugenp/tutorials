package com.baeldung.hexagonal.adapters;

import com.baeldung.hexagonal.domain.ChatMessage;
import com.baeldung.hexagonal.domain.IDisplayMessages;
import com.baeldung.hexagonal.domain.IStoreMessages;

/**
 * A Utility adapter to display the chat messages in the console
 */
public class DisplayInConsoleAdapter implements IDisplayMessages {

    IStoreMessages messageStore;

    public DisplayInConsoleAdapter(IStoreMessages messageStore) {
        this.messageStore = messageStore;
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    @Override
    public void displayMessages() {
        clearScreen();
        for (ChatMessage message: messageStore.getMessages(10)) {
            System.out.printf(
                "%tF %tT [%s to %s]: %s %n",
                message.getTimeSent(),
                message.getTimeSent(),
                message.getFrom(),
                message.getTo(),
                message.getContents());
        }
    }
}
