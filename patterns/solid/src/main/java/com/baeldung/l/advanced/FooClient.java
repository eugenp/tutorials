package com.baeldung.l.advanced;

import java.util.ArrayList;
import java.util.List;

public class FooClient {
    public static void main(String[] args) {
        List<Foo> foos = new ArrayList<>();
        foos.add(new Foo());
        foos.add(new Bar());

        // This works fine for both Foo & Bar instances
        foos.forEach(foo -> foo.doStuff(4));

        // This call fails on the Bar instance
        foos.forEach(foo -> foo.doOtherStuff(4));
    }
}