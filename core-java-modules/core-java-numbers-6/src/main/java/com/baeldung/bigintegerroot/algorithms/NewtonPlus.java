package com.baeldung.bigintegerroot.algorithms;

import java.math.BigInteger;

public class NewtonPlus {

    private NewtonPlus() {
    }

    // A fast square root by Ryan Scott White.
    public static BigInteger sqrt(BigInteger x) {
        if (x.compareTo(BigInteger.valueOf(144838757784765629L)) < 0) {
            long xAsLong = x.longValue();
            long vInt = (long)Math.sqrt(xAsLong);
            if (vInt * vInt > xAsLong)
                vInt--;
            return BigInteger.valueOf(vInt); }

        double xAsDub = x.doubleValue();
        BigInteger val;
        if (xAsDub < 2.1267e37) // 2.12e37 largest here
        // since sqrt(long.max*long.max) > long.max
        {
            long vInt = (long)Math.sqrt(xAsDub);
            val = BigInteger.valueOf
                ((vInt + x.divide(BigInteger.valueOf(vInt)).longValue()) >> 1);
        }
        else if (xAsDub < 4.3322e127) {
            // Convert a double to a BigInteger
            long bits = Double.doubleToLongBits(Math.sqrt(xAsDub));
            int exp = ((int) (bits >> 52) & 0x7ff) - 1075;
            val = BigInteger.valueOf((bits & ((1L << 52)) - 1) | (1L << 52)).shiftLeft(exp);

            val = x.divide(val).add(val).shiftRight(1);
            if (xAsDub > 2e63) {
                val = x.divide(val).add(val).shiftRight(1); }
        }
        else // handle large numbers over 4.3322e127
        {
            int xLen = x.bitLength();
            int wantedPrecision = ((xLen + 1) / 2);
            int xLenMod = xLen + (xLen & 1) + 1;

            //////// Do the first Sqrt on Hardware ////////
            long tempX = x.shiftRight(xLenMod - 63).longValue();
            double tempSqrt1 = Math.sqrt(tempX);
            long valLong = Double.doubleToLongBits(tempSqrt1) & 0x1fffffffffffffL;

            if (valLong == 0)
                valLong = 1L << 53;

            //////// Classic Newton Iterations ////////
            val = BigInteger.valueOf(valLong).shiftLeft(53 - 1)
                .add((x.shiftRight(xLenMod -
                                   (3 * 53))).divide(BigInteger.valueOf(valLong)));

            int size = 106;
            for (; size < 256; size <<= 1) {
                val = val.shiftLeft(size - 1).add(x.shiftRight
                    (xLenMod - (3*size)).divide(val));}

            if (xAsDub > 4e254) { // 4e254 = 1<<845.77
                int numOfNewtonSteps = 31 -
                                       Integer.numberOfLeadingZeros(wantedPrecision / size)+1;

                ////// Apply Starting Size ////////
                int wantedSize = (wantedPrecision >> numOfNewtonSteps) + 2;
                int needToShiftBy = size - wantedSize;
                val = val.shiftRight(needToShiftBy);

                size = wantedSize;
                do {
                    //////// Newton Plus Iteration ////////
                    int shiftX = xLenMod - (3 * size);
                    BigInteger valSqrd = val.multiply(val).shiftLeft(size - 1);
                    BigInteger valSU = x.shiftRight(shiftX).subtract(valSqrd);
                    val = val.shiftLeft(size).add(valSU.divide(val));
                    size *= 2;
                } while (size < wantedPrecision);
            }
            val = val.shiftRight(size - wantedPrecision);
        }

        // Detect a round ups. This function can be further optimized - see article.
        // For a ~7% speed bump the following line can be removed but round-ups will occur.
        if (val.multiply(val).compareTo(x) > 0)
            val = val.subtract(BigInteger.ONE);

        // Enabling the below will guarantee an error is stopped for larger numbers.
        // Note: As of this writing, there are no known errors.
        BigInteger tmp = val.multiply(val);
        if (tmp.compareTo(x) > 0) {
            System.out.println("val^2(" + val.multiply(val).toString()
                               + ") ≥ x(" + x.toString()+")");
            System.console().readLine();
            //throw new Exception("Sqrt function had internal error - value too high");
        }
        if (tmp.add(val.shiftLeft(1)).add(BigInteger.ONE).compareTo(x) <= 0) {
            System.out.println("(val+1)^2("
                               + val.add(BigInteger.ONE).multiply(val.add(BigInteger.ONE)).toString()
                               + ") ≥ x(" + x.toString() + ")");
            System.console().readLine();
            //throw new Exception("Sqrt function had internal error - value too low");
        }

        return val;
    }
}
