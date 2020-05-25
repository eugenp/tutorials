package com.baeldung.formatNumber;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class FormatNumber {
        private static final double D = 4.2352989244d;
        private static final double F = 8.6994540927d;

        public static void main(String[] args) {
                System.out.println(D + " with Big Decimal (2 places) is " + withBigDecimal(D, 2));
                System.out.println(D + " with Big Decimal (3 places) is " + withBigDecimal(D, 3));
                System.out.println(F + " with Big Decimal (2 places) is " + withBigDecimal(F, 2));
                System.out.println(F + " with Big Decimal (3 places) is " + withBigDecimal(F, 3));
                System.out.println(D + " with Math.round is (2 places) " + withMathRound(D, 2));
                System.out.println(D + " with Math.round is (3 places) " + withMathRound(D, 3));
                System.out.println(F + " with Math.round is (2 places) " + withMathRound(F, 2));
                System.out.println(F + " with Math.round is (3 places) " + withMathRound(F, 3));
                System.out.println(D + " with String Format is (2 places) " + withStringFormat(D, 2));
                System.out.println(D + " with String Format is (3 places) " + withStringFormat(D, 3));
                System.out.println(F + " with String Format is (2 places) " + withStringFormat(F, 2));
                System.out.println(F + " with String Format is (3 places) " + withStringFormat(F, 3));
                System.out.println(D + " with Decimal Format (local) is " + withDecimalFormatLocal(D));
                System.out.println(D + " with Decimal Format (2 places) is " + withDecimalFormatPattern(D, 2));
                System.out.println(D + " with Decimal Format (3 places) is " + withDecimalFormatPattern(D, 3));
                System.out.println(F + " with Decimal Format is (local) " + withDecimalFormatLocal(F));
                System.out.println(F + " with Decimal Format is (2 places) " + withDecimalFormatPattern(F, 2));
                System.out.println(F + " with Decimal Format is (3 places) " + withDecimalFormatPattern(F, 3));
        }

        public static double withBigDecimal(double value, int places) {
                if (places < 0)
                        throw new IllegalArgumentException();

                BigDecimal bigDecimal = new BigDecimal(value);
                bigDecimal = bigDecimal.setScale(places, RoundingMode.HALF_UP);
                return bigDecimal.doubleValue();
        }

        public static double withMathRound(double value, int places) {
                double scale = Math.pow(10, places);
                return Math.round(value * scale) / scale;
        }

        public static double withDecimalFormatPattern(double value, int places) {
                DecimalFormat df2 = new DecimalFormat("#,###,###,##0.00");
                DecimalFormat df3 = new DecimalFormat("#,###,###,##0.000");
                if (places == 2)
                        return new Double(df2.format(value));
                if (places == 3)
                        return new Double(df3.format(value));
        }

        public static double withDecimalFormatLocal(double value) {
                DecimalFormat df = (DecimalFormat) NumberFormat.getNumberInstance(Locale.getDefault());
                return new Double(df.format(value));
        }

        public static String withStringFormat(double value, int places) {
                return String.format("%." + places + "f", value);
        }
}
