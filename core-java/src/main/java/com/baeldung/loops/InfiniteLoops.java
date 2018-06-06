package com.baeldung.loops;

public class InfiniteLoops {

    public void infiniteLoopUsingWhile() {
        while (true) {
            System.out.println("Infinite loop using while");
        }
    }

    public void infiniteLoopUsingFor() {
        for (;;) {
            System.out.println("Infinite loop using for");
        }
    }

    public void infiniteLoopUsingDoWhile() {
        do {
            System.out.println("Infinite loop using do-while");
        } while (true);
    }

}
