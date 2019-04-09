package com.baeldung.nan;

/**
 * Sample usage of NaN.
 *
 */
public class NaNExample {

    public static void main(String[] args) {
        NaNExample naNExample = new NaNExample();
        naNExample.demo();
    }

    void demo() {
        undefined_operations_produce_NaN();
        operations_with_no_real_results_produce_NaN();
        operations_with_NaN_produce_NaN();
        comparison_with_NaN();
        check_if_a_value_is_NaN();
        assign_NaN_to_missing_values();
    }

    void undefined_operations_produce_NaN() {
        System.out.println("Undefined Operations Produce NaN");
        final double ZERO = 0;
        System.out.println("ZERO / ZERO = " + (ZERO / ZERO));
        System.out.println("INFINITY - INFINITY = " + (Double.POSITIVE_INFINITY - Double.POSITIVE_INFINITY));
        System.out.println("INFINITY * ZERO = " + (Double.POSITIVE_INFINITY * ZERO));
        System.out.println();
    }

    void operations_with_no_real_results_produce_NaN() {
        System.out.println("Operations with no real results produce NaN");
        System.out.println("SQUARE ROOT OF -1 = " + Math.sqrt(-1));
        System.out.println("LOG OF -1 = " + Math.log(-1));
        System.out.println();
    }

    void operations_with_NaN_produce_NaN() {
        System.out.println("Operations with NaN produce NaN");
        System.out.println("2 + NaN = " + (2 + Double.NaN));
        System.out.println("2 - NaN = " + (2 - Double.NaN));
        System.out.println("2 * NaN = " + (2 * Double.NaN));
        System.out.println("2 / NaN = " + (2 / Double.NaN));
        System.out.println();
    }

    void assign_NaN_to_missing_values() {
        System.out.println("Assign NaN to Missing values");
        double salaryRequired = Double.NaN;
        System.out.println(salaryRequired);
        System.out.println();
    }

    void comparison_with_NaN() {
        System.out.println("Comparison with NaN");
        final double NAN = Double.NaN;
        System.out.println("NaN == 1 = " + (NAN == 1));
        System.out.println("NaN > 1 = " + (NAN > 1));
        System.out.println("NaN < 1 = " + (NAN < 1));
        System.out.println("NaN != 1 = " + (NAN != 1));
        System.out.println("NaN == NaN = " + (NAN == NAN));
        System.out.println("NaN > NaN = " + (NAN > NAN));
        System.out.println("NaN < NaN = " + (NAN < NAN));
        System.out.println("NaN != NaN = " + (NAN != NAN));
        System.out.println();
    }

    void check_if_a_value_is_NaN() {
        System.out.println("Check if a value is NaN");
        double x = 1;
        System.out.println(x + " is NaN = " + (x != x));
        System.out.println(x + " is NaN = " + (Double.isNaN(x)));

        x = Double.NaN;
        System.out.println(x + " is NaN = " + (x != x));
        System.out.println(x + " is NaN = " + (Double.isNaN(x)));
        System.out.println();
    }

}
