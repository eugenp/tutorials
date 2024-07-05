package com.baeldung.spotless;

import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.lang.annotation.Documented;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class FizzBuzz {

	@Nullable
	static String pi = "3.14";

    public static void main(String[] args) {
        Map<Integer, String> dividers = new HashMap<>();
        dividers.put(3, "Fizz");
        dividers.put(5, "Buzz");

        int limit = 100;

        for (int i = 1; i <= limit; i++) {
            StringBuilder output = new StringBuilder();

            for (Map.Entry<Integer, String> entry : dividers.entrySet()) {
                if (i % entry.getKey() == 0) {
                    output.append(entry.getValue());
                }
            }
            if (!(output.length() == 0)) {
                System.out.println(output);
            } else {
                System.out.println(i);
            }
        }
    }

}
