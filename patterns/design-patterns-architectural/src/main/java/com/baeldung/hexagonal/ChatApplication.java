package com.baeldung.hexagonal;

import com.baeldung.hexagonal.adapters.DisplayInConsoleAdapter;
import com.baeldung.hexagonal.adapters.InMemoryMessageStore;
import com.baeldung.hexagonal.application.ChatManager;
import com.baeldung.hexagonal.domain.ChatUser;
import com.baeldung.hexagonal.domain.IDisplayMessages;

import java.util.ArrayDeque;
import java.util.Scanner;

public class ChatApplication {

    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);

        System.out.print("Enter username of user 1: ");
        ChatUser user1 = new ChatUser(console.nextLine());

        System.out.printf("Enter username of user 2: ");
        ChatUser user2 = new ChatUser(console.nextLine());

        System.out.println("Chat will end when any user uses the word bye in a message.");

        InMemoryMessageStore messageStore = new InMemoryMessageStore(new ArrayDeque<>(10));
        IDisplayMessages messageDisplayer = new DisplayInConsoleAdapter(messageStore);
        ChatManager chatManager = new ChatManager(messageStore, messageDisplayer);


        while (true) {
            System.out.printf("From %s to %s : ", user1, user2);
            String message = console.nextLine();
            if (message.toLowerCase().contains("bye")) {
                chatManager.sendMessage(user1, user2, message);
                System.out.println("Chat recap:");
                messageDisplayer.displayMessages();
                System.exit(0);
            }
            chatManager.sendMessage(user1, user2, message);
            ChatUser temp = user1;
            user1 = user2;
            user2 = temp;
        }
    }
}
