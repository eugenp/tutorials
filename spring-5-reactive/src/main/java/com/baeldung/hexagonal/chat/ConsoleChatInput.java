package com.baeldung.hexagonal.chat;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

class ConsoleChatInput {
    private ChatInputReciever chatInputReciver;
    private Scanner scanner;

    public ConsoleChatInput() {
        this.scanner = new Scanner(System.in);
    }

    public void GetConsoleInput() {
        String recipientString = scanner.nextLine(); // Of the form "tom;dick;harry"
        String message = scanner.nextLine();
        List<String> recipients = new ArrayList<String>(Arrays.asList(recipientString.split(";")));

        chatInputReciver.onChatMessageInput(message, recipients);
    }
}
