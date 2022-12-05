package com.baeldung.instanceofalternative.enumallt;

public enum DinosaurEnum {
    Anatotitan {
        @Override
        public String move() {
            return "running";
        }
    },
    Euraptor {
        @Override
        public String move() {
            return "flying";
        }
    };

    public abstract String move();

}
