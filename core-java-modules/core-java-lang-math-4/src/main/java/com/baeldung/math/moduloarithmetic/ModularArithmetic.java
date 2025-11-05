package com.baeldung.math.moduloarithmetic;

public class ModularArithmetic {

    private static final int MOD = 1000000007;

    private static int minSymmetricMod(int x) {
        if (Math.abs(x % MOD) <= Math.abs((MOD - x) % MOD)) {
            return x % MOD;
        } else {
            return -1 * ((MOD - x) % MOD);
        }
    }

    private static int minSymmetricMod(long x) {
        if (Math.abs(x % MOD) <= Math.abs((MOD - x) % MOD)) {
            return (int) (x % MOD);
        } else {
            return (int) (-1 * ((MOD - x) % MOD));
        }
    }

    public static int mod(int x) {
        if (x >= 0) {
            return (x % MOD);
        } else {
            return mod(MOD + x);
        }
    }

    public static int mod(long x) {
        if (x >= 0) {
            return (int) (x % (long) MOD);
        } else {
            return mod(MOD + x);
        }
    }

    public static int modSum(int a, int b) {
        return mod(minSymmetricMod(a) + minSymmetricMod(b));
    }

    public static int modSubtract(int a, int b) {
        return mod(minSymmetricMod(a) - minSymmetricMod(b));
    }

    public static int modMultiply(int a, int b) {
        int result = minSymmetricMod((long) minSymmetricMod(a) * (long) minSymmetricMod(b));
        return mod(result);
    }

    public static int modPower(int base, int exp) {
        int result = 1;
        int b = base;
        while (exp > 0) {
            if ((exp & 1) == 1) {
                result = minSymmetricMod((long) minSymmetricMod(result) * (long) minSymmetricMod(b));
            }
            b = minSymmetricMod((long) minSymmetricMod(b) * (long) minSymmetricMod(b));
            exp >>= 1;
        }
        return mod(result);
    }

    private static int[] extendedGcd(int a, int b) {
        if (b == 0) {
            return new int[] { a, 1, 0 };
        }
        int[] result = extendedGcd(b, a % b);
        int gcd = result[0];
        int x = result[2];
        int y = result[1] - (a / b) * result[2];
        return new int[] { gcd, x, y };
    }

    public static int modInverse(int a) {
        int[] result = extendedGcd(a, MOD);
        int x = result[1];
        return mod(x);
    }

    public static int modDivide(int a, int b) {
        return modMultiply(minSymmetricMod(a), minSymmetricMod(modInverse(b)));
    }
}
