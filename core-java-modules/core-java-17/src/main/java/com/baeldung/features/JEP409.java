package com.baeldung.features;

public class JEP409 {

    sealed interface Shape permits Rectangle, Circle, Square, Triangle {
        int getNumberOfSides();
    }

    static final class Rectangle implements Shape {
        @Override
        public int getNumberOfSides() {
            return 4;
        }
    }

    static final class Circle implements Shape {
        @Override
        public int getNumberOfSides() {
            return 0;
        }
    }

    static final class Square implements Shape {
        @Override
        public int getNumberOfSides() {
            return 4;
        }
    }

    static non-sealed class Triangle implements Shape {

        @Override
        public int getNumberOfSides() {
            return 3;
        }
    }

}
