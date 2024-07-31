package com.baeldung.enumtostring;

public enum FastFood2 {
    PIZZA {
        @Override
        public String toString() {
            return "Pizza Pie";
        }
    },
    BURGER {
        @Override
        public String toString() {
            return "Cheese Burger";
        }
    },
    TACO {
        @Override
        public String toString() {
            return "Crunchy Taco";
        }
    },
    CHICKEN {
        @Override
        public String toString() {
            return "Fried Chicken";
        }
    }
}
