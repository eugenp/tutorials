package com.baeldung.reactive.client;

/**
 * A program that set up the client for consuming the stream produced in {@link StreamController}.
 */
public class Main {

    public static void main(String[] args) {
        Client client = new Client();
        client.consume();

        while (true) {
        }

    }

}
