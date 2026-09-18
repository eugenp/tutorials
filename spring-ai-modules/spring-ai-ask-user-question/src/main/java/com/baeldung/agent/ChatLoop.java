package com.baeldung.agent;

import java.util.Scanner;

import org.springaicommunity.agent.tools.AskUserQuestionTool.InvalidUserAnswerException;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;

@Component
class ChatLoop implements CommandLineRunner {

    private final ChatClient chatClient;

    ChatLoop(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @Override
    public void run(String... args) {
        System.out.println("AI Agent (type 'exit' to quit)\n");
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.print("You: ");
                String input = scanner.nextLine();
                if ("exit".equalsIgnoreCase(input.trim())) break;

                try {
                    String response = chatClient.prompt()
                        .user(input)
                        .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, "session-1"))
                        .call()
                        .content();
                    System.out.println("AI: " + response + "\n");
                }
                catch (InvalidUserAnswerException e) {
                    System.out.println("AI: Sorry, I couldn't process your answer: " + e.getMessage() + "\n");
                }
                catch (ResourceAccessException e) {
                    System.out.println("\nError: Unable to connect to the model server.");
                    System.out.println("Please check that the server is running and accessible.\n");
                    System.out.println("Type 'exit' to quit or try again.");
                }
                catch (Exception e) {
                    System.out.println("\nError: An unexpected error occurred: " + e.getMessage());
                    System.out.println("Type 'exit' to quit or try again.");
                }
            }
        }
    }
}