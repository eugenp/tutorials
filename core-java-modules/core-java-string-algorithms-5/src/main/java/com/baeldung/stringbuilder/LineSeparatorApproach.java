package com.example;

public class LineSeparatorApproach {
    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder(
            "StringBuilder\nLine Separator Approach\r\nLine by Line Reading\rAnother line"
        );

        // \R matches any line break (\n, \r\n, \r)
        String[] lines = sb.toString().split("\\R");

        for (String line : lines) {
            System.out.println(line);
        }
    }
}
