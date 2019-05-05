package com.baeldung.reducingIfElse;

public class Expression {

        private Integer x;
        private Integer y;
        private Operator operator;

        public Expression(Integer x, Integer y, Operator operator) {
                this.x = x;
                this.y = y;
                this.operator = operator;
        }

        public Integer getX() {
                return x;
        }

        public Integer getY() {
                return y;
        }

        public Operator getOperator() {
                return operator;
        }
}
