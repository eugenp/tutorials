package com.baeldung.threading;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.Socket;

public class ClientConnection implements Closeable {

    private final Socket socket;
    private final BufferedReader reader;
    private final PrintWriter writer;

    public ClientConnection(Socket socket) throws IOException {
        this.socket = socket;
        this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.writer = new PrintWriter(socket.getOutputStream(), true);
    }

    public Socket getSocket() {
        return socket;
    }

    public BufferedReader getReader() {
        return reader;
    }

    public PrintWriter getWriter() {
        return writer;
    }

    @Override
    public void close() throws IOException {
        try (Writer writer = this.writer; Reader reader = this.reader; Socket socket = this.socket) {
            // resources all closed when this block exits
        }
    }
}