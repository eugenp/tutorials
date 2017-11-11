package com.baeldung.breakcontinue;

/**
 * @author Santosh
 *
 */
public class BreakContinue {

    public static int unlabeledBreak() {
        int counter = 0;

        for (; counter < 5; counter++) {
            if (counter == 3) {
                break;
            }
        }
        return counter;
    }

    public static int unlabeledBreakNestedLoops() {
        int counter = 0;

        for (int rowNum = 0; rowNum < 3; rowNum++) {
            for (int colNum = 0; colNum < 4; colNum++) {
                if (colNum == 3) {
                    counter = colNum;
                    break;
                }
            }
        }
        return counter;
    }

    public static int labeledBreak() {
        int counter = 0;

        compare: 
        for (int rowNum = 0; rowNum < 3; rowNum++) {
            for (int colNum = 0; colNum < 4; colNum++) {
                if (rowNum == 1 && colNum == 3) {
                    counter = rowNum + colNum;
                    break compare;
                }
            }
        }
        return counter;
    }

    public static int unlabeledContinue() {
        int counter = 0;

        for (int rowNum = 0; rowNum < 3; rowNum++) {
            for (int colNum = 0; colNum < 4; colNum++) {
                if (colNum != 3) {
                    continue;
                }
                counter++;
            }
        }
        return counter;
    }

    public static int labeledContinue() {
        int counter = 0;

        compare: 
        for (int rowNum = 0; rowNum < 3; rowNum++) {
            for (int colNum = 0; colNum < 4; colNum++) {
                if (colNum == 3) {
                    counter++;
                    continue compare;
                }
            }
        }
        return counter;
    }

}
