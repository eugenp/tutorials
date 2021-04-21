package com.baeldung.hexagonal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class ConsoleProcessUserInputImpl implements ProcessUserInput {

    private static final String POSITIVE_ANSWER = "Y";
    private static final String NEGATIVE_ANSWER = "N";

    @Override
    public boolean processUserInput() {
        System.out.println("Show top 5 books of all time? Y/N");

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String answer = NEGATIVE_ANSWER;
        try {
            answer = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return POSITIVE_ANSWER.equals(answer);
    }

    @Override
    public void presentError(String message) {
        System.err.println(message);
    }
}