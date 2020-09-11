package main.java.com.baeldung.adapter;

import java.util.Scanner;

import main.java.com.baeldung.core.domain.Message;
import main.java.com.baeldung.core.service.EncodeServiceImpl;
import main.java.com.baeldung.port.ConsolePort;
import main.java.com.baeldung.port.EncodeService;

public class ConsoleAdapter implements ConsolePort {

    private static ConsoleAdapter consoleAdapter = new ConsoleAdapter();

    private EncodeService encodeService = new EncodeServiceImpl();

    @Override
    public Message getEncodedMessage(String textToEncode) {
        return encodeService.encodeMessage(textToEncode);
    }

    @Override
    public Message getDecodedMessage(String textToDecode) {
        return encodeService.decodeMessage(textToDecode);
    }

    public static void main(String[] args) {

        System.out.print("Enter a Message to Encode : ");
        Scanner scanner = new Scanner(System.in);
        String inputText = scanner.nextLine();

        Message message = consoleAdapter.getEncodedMessage(inputText);
        System.out.println("Your encoded Message : "+message);

        System.out.print("Enter a Message to Decode : ");
        inputText = scanner.nextLine();

        message = consoleAdapter.getDecodedMessage(inputText);
        System.out.println("Your Decoded Message : "+message);

    }

}
