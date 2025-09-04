package com.example;

import java.io.BufferedReader;
import java.io.StringReader;
import java.io.IOException;

public class BufferedReaderApproach {

    public static void main(String[] args) {

        StringBuilder sb = new StringBuilder(
            "StringBuilder\nBufferedReader Approach\r\nLine by Line Reading\rAnother line"
        );

        try (BufferedReader reader = new BufferedReader(new StringReader(sb.toString()))) {

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
