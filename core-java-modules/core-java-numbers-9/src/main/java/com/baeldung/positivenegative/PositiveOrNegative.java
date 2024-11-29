package com.baeldung.positivenegative;

public class PositiveOrNegative {
    enum Result {
        POSITIVE, NEGATIVE, ZERO
    }

    public static Result byOperator(Integer integer) {
        if (integer > 0) {
            return Result.POSITIVE;
        } else if (integer < 0) {
            return Result.NEGATIVE;
        }
        return Result.ZERO;
    }

    public static Result bySignum(Integer integer) {
        int result = Integer.signum(integer);

        if (result == 1) {
            return Result.POSITIVE;
        } else if (result == -1) {
            return Result.NEGATIVE;
        }
        return Result.ZERO;
    }

    public static Result bySignum(Float floatNumber) {
        Float result = Math.signum(floatNumber);

        if (result.compareTo(1.0f) == 0) {
            return Result.POSITIVE;
        } else if (result.compareTo(-1.0f) == 0) {
            return Result.NEGATIVE;
        }
        return Result.ZERO;
    }
}
