package com.baeldung.breakcontinue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author Santosh
 *
 */
public class BreakContinue {

    //number to be compared in all the methods
    private static int searchNumber = 13;
    
    //declare 2-dimensional array
    private static int[][] numbers = new int[3][4];

    static {
        //populate first row of the 2-dimensional array 
        numbers[0][0] = 11;
        numbers[0][1] = 12;
        numbers[0][2] = 13;
        numbers[0][3] = 14;

        //populate second row of the 2-dimensional array
        numbers[1][0] = 11;
        numbers[1][1] = 12;
        numbers[1][2] = 13;
        numbers[1][3] = 14;

        //populate third row of the 2-dimensional array
        numbers[2][0] = 11;
        numbers[2][1] = 12;
        numbers[2][2] = 13;
        numbers[2][3] = 14;
    }

    public static int unlabeledBreak() {
        int counter = 0;

        for (int number : numbers[0]) {
            counter++;
            if (number == searchNumber) {
                break;
            }
        }
        return counter;
    }

    public static int unlabeledBreakNestedLoops() {
        int counter = 0;

        for (int rowNum = 0; rowNum < 3; rowNum++) {
            for (int colNum = 0; colNum < 4; colNum++) {
                int number = numbers[rowNum][colNum];
                if (number == searchNumber) {
                    counter++;
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
                int number = numbers[rowNum][colNum];
                if (number == searchNumber) {
                    counter++;
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
                int number = numbers[rowNum][colNum];
                if (number != searchNumber) {
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
                int number = numbers[rowNum][colNum];
                if (number == searchNumber) {
                    counter++;
                    continue compare;
                }
            }
        }
        return counter;
    }

}
