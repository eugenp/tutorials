package com.baeldung.reducingIfElse;

public enum Operator {

    ADD {
        @Override
        public int apply(int a, int b) {
            return a + b;
        }
    },

    MULTIPLY {
        @Override
        public int apply(int a, int b) {
            return a * b;
        }
    },

    SUBTRACT {
        @Override
        public int apply(int a, int b) {
            return a - b;
        }
    },

    DIVIDE {
        @Override
        public int apply(int a, int b) {
            return a / b;
        }
    },

    MODULO {
        @Override
        public int apply(int a, int b) {
            return a % b;
        }
    };

    public abstract int apply(int a, int b);
}
