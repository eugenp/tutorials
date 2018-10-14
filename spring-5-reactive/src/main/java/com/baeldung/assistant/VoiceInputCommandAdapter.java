package com.baeldung.assistant;

/**
 * Example adapter that listens text message from microphone and sends it to CommandListenerPort of Assistant
 */
class VoiceInputCommandAdapter {
    private CommandListenerPort port;

    public VoiceInputCommandAdapter(CommandListenerPort port) {
        setPort(port);
    }

    public void setPort(CommandListenerPort port) {
        if (port == null)
            throw new IllegalArgumentException("CommandListenerPort cannot be null!");
        this.port = port;
    }

    public void speechFinished() {
        String text = "";
        // ... speech recognition, transform to text message
        port.onUserCommand(text);
    }
}
