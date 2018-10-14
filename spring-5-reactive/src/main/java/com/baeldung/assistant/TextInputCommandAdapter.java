package com.baeldung.assistant;

import java.util.Scanner;

/**
 * Example adapter that reads text message from standard input (console) and sends it to CommandListenerPort of Assistant
 */
class TextInputCommandAdapter {
    private CommandListenerPort port;
    private final Scanner s;

    public TextInputCommandAdapter(CommandListenerPort port) {
        setPort(port);
        this.s = new Scanner(System.in);
    }

    public void setPort(CommandListenerPort port) {
        if (port == null)
            throw new IllegalArgumentException("CommandListenerPort cannot be null!");
        this.port = port;
    }

    /**
     * Call this method when user finishes to type a message (on SEND button)
     */
    public void inputFinished() {
        String text = s.next();
        port.onUserCommand(text);
    }
}
