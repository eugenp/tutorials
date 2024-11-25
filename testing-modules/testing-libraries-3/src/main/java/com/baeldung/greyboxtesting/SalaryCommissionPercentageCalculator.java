package com.baeldung.greyboxtesting;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.stream.DoubleStream;

public class SalaryCommissionPercentageCalculator {
    public BigDecimal calculate(Level level, Type type, Seniority seniority, SalesImpact impact) {
        return BigDecimal.valueOf(DoubleStream.of(level.getBonus(), type.getBonus(), seniority.getBonus(), impact.getBonus(), type.getBonus())
                .average()
                .orElse(0))
                .setScale(2, RoundingMode.CEILING);
    }

    public enum Level {
        L1(0.06), L2(0.12), L3(0.2);
        private double bonus;

        Level(double bonus) {
            this.bonus = bonus;
        }

        public double getBonus() {
            return bonus;
        }
    }

    public enum Type {
        FULL_TIME_COMMISSIONED(0.18), CONTRACTOR(0.1), FREELANCER(0.06);

        private double bonus;

        Type(double bonus) {
            this.bonus = bonus;
        }

        public double getBonus() {
            return bonus;
        }
    }

    public enum Seniority {
        JR(0.8), MID(0.13), SR(0.19);
        private double bonus;

        Seniority(double bonus) {
            this.bonus = bonus;
        }

        public double getBonus() {
            return bonus;
        }
    }

    public enum SalesImpact {
        LOW(0.06), MEDIUM(0.12), HIGH(0.2);
        private double bonus;

        SalesImpact(double bonus) {
            this.bonus = bonus;
        }

        public double getBonus() {
            return bonus;
        }
    }
}
