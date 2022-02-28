package com.baeldung.exception.variablemightnothavebeeninitialized;

public class VariableMightNotHaveBeenInitialized {

    private static int instanceVariableCount;

    /**
     * Method would not compile if lines 14 and 18 are uncommented.
     */
    public static void countEven() {
        //uninstantiated
        int count;
        int[] arr = new int[]{23, 56, 89, 12, 23};
        for (int i = 0; i < arr.length; i++) {
            if ((arr[i] % 2) == 0) {
                // count++;
            }

        }
        // System.out.println("Total Even Numbers : " + count);
    }

    public static int countEvenUsingInstanceVariable(int[] arr) {

        for (int i = 0; i < arr.length; i++) {
            if ((arr[i] % 2) == 0) {
                instanceVariableCount++;
            }

        }
        return instanceVariableCount;
    }

    public static int countEvenUsingIfElse(int[] arr, int args) {
        int count;
        count = args > 0 ? args : 0;
        for (int i = 0; i < arr.length; i++) {
            if ((arr[i] % 2) == 0) {
                count++;
            }

        }
        return count;
    }
}
