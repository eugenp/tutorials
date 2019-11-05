package com.baeldung.systemgc;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static java.util.UUID.randomUUID;

public class DemoApplication {

    private static final Map<String, String> cache = new HashMap<String, String>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()) {
            final String next = scanner.next();
            if ("fill".equals(next)) {
                for (int i = 0; i < 1000000; i++) {
                    cache.put(randomUUID().toString(), randomUUID().toString());
                }
            } else if ("invalidate".equals(next)) {
                cache.clear();
            } else if ("gc".equals(next)) {
                System.gc();
            } else if ("exit".equals(next)) {
                System.exit(0);
            } else {
                System.out.println("unknown");
            }
        }
    }
}