package com.baeldung.functional.functors;

public enum EnumFunctor {
    PLUS {
        public int apply(int a, int b) {
            return a + b;
        }
    }, MINUS {
        public int apply(int a, int b) {
            return a - b;
        }
    }, MULTIPLY {
        public int apply(int a, int b) {
            return a * b;
        }
    }, DIVIDE {
        public int apply(int a, int b) {
            return a / b;
        }
    };

    public abstract int apply(int a, int b);
}