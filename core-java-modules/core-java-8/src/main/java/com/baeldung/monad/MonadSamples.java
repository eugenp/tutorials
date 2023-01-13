package com.baeldung.monad;

import java.util.Optional;
import java.util.function.Function;

class MonadBaseExample {

    public double multiplyBy2(double n) {
        return n * 2;
    }

    public double divideBy2(double n) {
        return n / 2;
    }

    public double add3(double n) {
        return n + 3;
    }

    public double subtract1(double n) {
        return n - 1;
    }

}

class MonadSample1 extends MonadBaseExample {

    public double apply(double n) {
        return subtract1(add3(divideBy2(multiplyBy2(multiplyBy2(2)))));
    }

    public static void main(String[] args) {
        final MonadSample1 test = new MonadSample1();
        System.out.println(test.apply(2));
        //6.0
    }

}

class MonadSample2 extends MonadBaseExample {
    public double apply(double n) {
        final double n1 = multiplyBy2(n);
        final double n2 = multiplyBy2(n1);
        final double n3 = divideBy2(n2);
        final double n4 = add3(n3);
        return subtract1(n4);
    }

    public static void main(String[] args) {
        final MonadSample2 test = new MonadSample2();
        System.out.println(test.apply(2));
        //6.0
    }

}

class MonadSample3 extends MonadBaseExample {

    public double apply(double n) {
        return Optional.of(n)
                .flatMap(value -> Optional.of(multiplyBy2(value)))
                .flatMap(value -> Optional.of(multiplyBy2(value)))
                .flatMap(value -> Optional.of(divideBy2(value)))
                .flatMap(value -> Optional.of(add3(value)))
                .flatMap(value -> Optional.of(subtract1(value)))
                .get();
    }

    public static void main(String[] args) {
        final MonadSample3 test = new MonadSample3();
        System.out.println(test.apply(2));
        //6.0
    }
}

 class MonadSample4 extends MonadBaseExample {
     public boolean leftIdentity() {
         Function<Integer, Optional<Integer>> mapping = value -> Optional.of(value + 1);
         return Optional.of(3).flatMap(mapping).equals(mapping.apply(3));
     }

     public boolean rightIdentity() {
         return Optional.of(3).flatMap(Optional::of).equals(Optional.of(3));
     }

     public boolean associativity() {
         Function<Integer, Optional<Integer>> mapping = value -> Optional.of(value + 1);
         Optional<Integer> leftSide = Optional.of(3).flatMap(mapping).flatMap(Optional::of);
         Optional<Integer> rightSide = Optional.of(3).flatMap(v -> mapping.apply(v).flatMap(Optional::of));
         return leftSide.equals(rightSide);
     }

     public static void main(String[] args) {
         final MonadSample4 test = new MonadSample4();

         System.out.println(test.leftIdentity()); //true
         System.out.println(test.rightIdentity()); //true
         System.out.println(test.associativity()); //true
     }
 }

class MonadSample5 extends MonadBaseExample {
    public boolean fail() {
        Function<Integer, Optional<Integer>> mapping = value -> Optional.of(value == null ? -1 : value + 1);
        return Optional.ofNullable((Integer) null).flatMap(mapping).equals(mapping.apply(null));
    }

    public static void main(String[] args) {
        final MonadSample5 test = new MonadSample5();
        System.out.println(test.fail());
    }
}

