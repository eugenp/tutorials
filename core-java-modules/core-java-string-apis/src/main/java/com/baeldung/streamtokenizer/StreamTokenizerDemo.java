package com.baeldung.streamtokenizer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class StreamTokenizerDemo {

    private static final String INPUT_FILE = "/stream-tokenizer-example.txt";
    private static final int QUOTE_CHARACTER = '\'';
    private static final int DOUBLE_QUOTE_CHARACTER = '"';

    public static List<Object> streamTokenizerWithDefaultConfiguration(Reader reader) throws IOException {
        StreamTokenizer streamTokenizer = new StreamTokenizer(reader);
        List<Object> tokens = new ArrayList<>();

        int currentToken = streamTokenizer.nextToken();
        while (currentToken != StreamTokenizer.TT_EOF) {

            if (streamTokenizer.ttype == StreamTokenizer.TT_NUMBER) {
                tokens.add(streamTokenizer.nval);
            } else if (streamTokenizer.ttype == StreamTokenizer.TT_WORD
                    || streamTokenizer.ttype == QUOTE_CHARACTER
                    || streamTokenizer.ttype == DOUBLE_QUOTE_CHARACTER) {
                tokens.add(streamTokenizer.sval);
            } else {
                tokens.add((char) currentToken);
            }

            currentToken = streamTokenizer.nextToken();
        }

        return tokens;
    }

    public static List<Object> streamTokenizerWithCustomConfiguration(Reader reader) throws IOException {
        StreamTokenizer streamTokenizer = new StreamTokenizer(reader);
        List<Object> tokens = new ArrayList<>();

        streamTokenizer.wordChars('!', '-');
        streamTokenizer.ordinaryChar('/');
        streamTokenizer.commentChar('#');
        streamTokenizer.eolIsSignificant(true);

        int currentToken = streamTokenizer.nextToken();
        while (currentToken != StreamTokenizer.TT_EOF) {

            if (streamTokenizer.ttype == StreamTokenizer.TT_NUMBER) {
                tokens.add(streamTokenizer.nval);
            } else if (streamTokenizer.ttype == StreamTokenizer.TT_WORD
                    || streamTokenizer.ttype == QUOTE_CHARACTER
                    || streamTokenizer.ttype == DOUBLE_QUOTE_CHARACTER) {
                tokens.add(streamTokenizer.sval);
            } else {
                tokens.add((char) currentToken);
            }
            currentToken = streamTokenizer.nextToken();
        }

        return tokens;
    }

    public static Reader createReaderFromFile() throws FileNotFoundException {
        String inputFile = StreamTokenizerDemo.class.getResource(INPUT_FILE).getFile();
        return new FileReader(inputFile);
    }

    public static void main(String[] args) throws IOException {
        List<Object> tokens1 = streamTokenizerWithDefaultConfiguration(createReaderFromFile());
        List<Object> tokens2 = streamTokenizerWithCustomConfiguration(createReaderFromFile());

        System.out.println(tokens1);
        System.out.println(tokens2);
    }

}