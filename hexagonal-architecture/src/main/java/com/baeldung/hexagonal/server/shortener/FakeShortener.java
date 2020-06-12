package com.baeldung.hexagonal.server.shortener;

import com.baeldung.hexagonal.domain.shortener.ShortenerService;

import java.util.Random;
import java.util.stream.IntStream;

public class FakeShortener implements ShortenerService {

    private char[] myChars;
    private Random random;
    private int codeLength;

    public FakeShortener() {
        myChars = new char[62];
        for (int i = 0; i < 62; i++) {
            int j;
            if (i < 10) {
                j = i + 48;
            } else if (i <= 35) {
                j = i + 55;
            } else {
                j = i + 61;
            }
            myChars[i] = (char) j;
        }

        codeLength = 8;
        random = new Random();
    }

    /**
     * This method will fake shorten the URL (it actually doesn't use the input URL)
     *
     * @param url the input URL we want to shorten
     * @return a code
     */
    @Override
    public String shortenUrl(String url) {
        StringBuilder key = new StringBuilder();
        IntStream.range(0, codeLength)
                .forEach(__ -> key.append(myChars[random.nextInt(62)]));
        return key.toString();
    }
}
