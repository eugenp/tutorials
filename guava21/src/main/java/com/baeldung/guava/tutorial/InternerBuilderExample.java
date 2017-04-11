package com.baeldung.guava.tutorial;

import com.google.common.collect.Interner;
import com.google.common.collect.Interners;

public class InternerBuilderExample {

    public static void main(String[] args) {
        Interner<Integer> interners = Interners.<Integer> newBuilder()
          .concurrencyLevel(2)
          .strong().<Integer> build();

    }

}
