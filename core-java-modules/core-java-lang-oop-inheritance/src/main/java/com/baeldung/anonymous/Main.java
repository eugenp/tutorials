package com.baeldung.anonymous;

import java.util.ArrayList;
import java.util.List;

/**
 * Code snippet that illustrates the usage of anonymous classes.
 * 
 * Note that use of Runnable instances in this example does not demonstrate their
 * common use.
 * 
 * @author A. Shcherbakov
 *
 */
public class Main {

    public static void main(String[] args) {
        final List<Runnable> actions = new ArrayList<Runnable>(2);

        Runnable action = new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello from runnable.");
            }

        };
        actions.add(action);

        Book book = new Book("Design Patterns") {
            @Override
            public String description() {
                return "Famous GoF book.";
            }
        };

        System.out.println(String.format("Title: %s, description: %s", book.title, book.description()));

        actions.add(new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello from runnable #2.");

            }
        });

        int count = 1;

        Runnable action2 = new Runnable() {
            static final int x = 0;
            // static int y = 0;

            @Override
            public void run() {
                System.out.println(String.format("Runnable with captured variables: count = %s, x = %s", count, x));
            }
        };
        actions.add(action2);

        for (Runnable a : actions) {
            a.run();
        }
    }

}
