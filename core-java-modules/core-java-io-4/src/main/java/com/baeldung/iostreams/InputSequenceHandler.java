package com.baeldung.iostreams;

import java.io.*;
import java.util.List;
import java.util.Vector;

public class InputSequenceHandler {

    private SequenceInputStream sequenceInputStream;

    public InputSequenceHandler(Vector<InputStream> inputStreams) {
        sequenceInputStream = new SequenceInputStream(inputStreams.elements());
    }

    public InputSequenceHandler(String file1, String file2) throws FileNotFoundException {
        sequenceInputStream = new SequenceInputStream(new FileInputStream(file1), new FileInputStream(file2));
    }

    public InputSequenceHandler(List<String> fileNames) throws FileNotFoundException {
        Vector<InputStream> inputStreams = new Vector<>();

        for (String fileName: fileNames) {
            inputStreams.add(new FileInputStream(fileName));
        }
        sequenceInputStream = new SequenceInputStream(inputStreams.elements());
    }


    public int read() throws IOException {
        return sequenceInputStream.read();
    }

    public String readAsString() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        int readByte;
        while ((readByte = sequenceInputStream.read()) != -1) {
            stringBuilder.append((char) readByte);
        }
        return stringBuilder.toString();
    }

    public void close() throws IOException {
        sequenceInputStream.close();
    }
}
