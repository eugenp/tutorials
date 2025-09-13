package com.example;

public class StreamLinesApproach {

    public static void main(String[] args) {

        StringBuilder sb = new StringBuilder(
            "StringBuilder\nStream Approach\r\nLine by Line Reading\rAnother line"
        );

        sb.toString()
          .lines()
          .forEach(System.out::println);

    }
}
