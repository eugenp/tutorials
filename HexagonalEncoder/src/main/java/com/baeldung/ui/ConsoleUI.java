package main.java.com.baeldung.ui;

import java.util.Scanner;

import main.java.com.baeldung.adapter.ConsoleAdapter;
import main.java.com.baeldung.core.domain.Message;

public class ConsoleUI {

    private ConsoleAdapter consoleAdapter = new ConsoleAdapter();

    public String getInputMessage() {
        System.out.print("Enter a Message: ");
        Scanner scanner = new Scanner(System.in);
        String inputText = scanner.nextLine();
        return inputText;
    }

    public Message processEncoding() {
        Message message = consoleAdapter.getEncodedMessage(getInputMessage());
        return message;
    }

    // ..decode UI code

    public static void main(String[] args) {

        // Test console UI
        ConsoleUI consoleUI = new ConsoleUI();
        System.out.println(consoleUI.processEncoding());
    }
}
