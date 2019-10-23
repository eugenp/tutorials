package com.stackify.optionalparams;

public class MultiVitaminWithBuilder {

    private final String name;    // required
    private final int vitaminA;   // in mcg
    private final int vitaminC;   // in mg
    private final int calcium;    // in mg
    private final int iron;       // in mg

    private MultiVitaminWithBuilder(MultiVitaminBuilder builder) {
        this.name = builder.name;
        this.vitaminA = builder.vitaminA;
        this.vitaminC = builder.vitaminC;
        this.calcium = builder.calcium;
        this.iron = builder.iron;
    }

    public String getName() {
        return name;
    }

    public int getVitaminA() {
        return vitaminA;
    }

    public int getVitaminC() {
        return vitaminC;
    }

    public int getCalcium() {
        return calcium;
    }

    public int getIron() {
        return iron;
    }

    public static class MultiVitaminBuilder {

        private static final int ZERO = 0;

        private final String name; // required
        private int vitaminA = ZERO;
        private int vitaminC = ZERO;
        private int calcium = ZERO;
        private int iron = ZERO;

        public MultiVitaminBuilder(String name) {
            this.name = name;
        }

        public MultiVitaminBuilder withVitaminA(int vitaminA) {
            this.vitaminA = vitaminA;
            return this;
        }

        public MultiVitaminBuilder withVitaminC(int vitaminC) {
            this.vitaminC = vitaminC;
            return this;
        }

        public MultiVitaminBuilder withCalcium(int calcium) {
            this.calcium = calcium;
            return this;
        }

        public MultiVitaminBuilder withIron(int iron) {
            this.iron = iron;
            return this;
        }

        public MultiVitaminWithBuilder build() {
            return new MultiVitaminWithBuilder(this);
        }
    }
}
