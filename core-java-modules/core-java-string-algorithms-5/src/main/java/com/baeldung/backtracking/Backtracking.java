package com.baeldung.backtracking;

import java.util.ArrayList;
import java.util.List;

public class Backtracking {

    private Backtracking() {
    }

    public static List<String> process(String digits, int target) {
        final List<String> validExpressions = new ArrayList<>();
        evaluateExpressions(validExpressions, new Equation(digits, target), 0, new StringBuilder(), 0, 0);
        return validExpressions;
    }

    private static void evaluateExpressions(List<String> validExpressions, Equation equation, int index, StringBuilder currentExpression, long currentResult,
        long lastOperand) {

        if (allDigitsProcessed(equation.getDigits(), index)) {
            if (currentResult == equation.getTarget()) {
                validExpressions.add(currentExpression.toString());
            }
            return;
        }

        exploreExpressions(validExpressions, equation, index, currentExpression, currentResult, lastOperand);
    }

    private static boolean allDigitsProcessed(String digits, int index) {
        return index == digits.length();
    }

    private static void exploreExpressions(List<String> validExpressions, Equation equation, int index, StringBuilder currentExpression, long currentResult,
        long lastOperand) {

        for (int endIndex = index; endIndex < equation.getDigits()
            .length(); endIndex++) {
            if (isWithLeadingZero(equation.getDigits(), index, endIndex)) {
                break;
            }

            long currentOperandValue = Long.parseLong(equation.getDigits()
                .substring(index, endIndex + 1));

            if (isFirstOperand(index)) {
                processFirstOperand(validExpressions, equation, endIndex, currentExpression, currentOperandValue);
            } else {
                applyAddition(validExpressions, equation, endIndex, currentExpression, currentResult, currentOperandValue);
                applySubtraction(validExpressions, equation, endIndex, currentExpression, currentResult, currentOperandValue);
                applyMultiplication(validExpressions, equation, endIndex, currentExpression, currentResult, currentOperandValue, lastOperand);
            }
        }
    }

    private static boolean isWithLeadingZero(String digits, int index, int endIndex) {
        return endIndex > index && digits.charAt(index) == '0';
    }

    private static boolean isFirstOperand(int index) {
        return index == 0;
    }

    private static void processFirstOperand(List<String> validExpressions, Equation equation, int endIndex, StringBuilder currentExpression,
        long currentOperandValue) {
        appendToExpression(currentExpression, Operator.NONE, currentOperandValue);

        evaluateExpressions(validExpressions, equation, endIndex + 1, currentExpression, currentOperandValue, currentOperandValue);

        removeFromExpression(currentExpression, Operator.NONE, currentOperandValue);
    }

    private static void applyAddition(List<String> validExpressions, Equation equation, int endIndex, StringBuilder currentExpression, long currentResult,
        long currentOperandValue) {
        appendToExpression(currentExpression, Operator.ADDITION, currentOperandValue);

        evaluateExpressions(validExpressions, equation, endIndex + 1, currentExpression, currentResult + currentOperandValue, currentOperandValue);

        removeFromExpression(currentExpression, Operator.ADDITION, currentOperandValue);
    }

    private static void applySubtraction(List<String> validExpressions, Equation equation, int endIndex, StringBuilder currentExpression, long currentResult,
        long currentOperandValue) {
        appendToExpression(currentExpression, Operator.SUBTRACTION, currentOperandValue);

        evaluateExpressions(validExpressions, equation, endIndex + 1, currentExpression, currentResult - currentOperandValue, -currentOperandValue);

        removeFromExpression(currentExpression, Operator.SUBTRACTION, currentOperandValue);
    }

    private static void applyMultiplication(List<String> validExpressions, Equation equation, int endIndex, StringBuilder currentExpression, long currentResult,
        long currentOperandValue, long lastOperand) {
        appendToExpression(currentExpression, Operator.MULTIPLICATION, currentOperandValue);

        evaluateExpressions(validExpressions, equation, endIndex + 1, currentExpression, currentResult - lastOperand + (lastOperand * currentOperandValue),
            lastOperand * currentOperandValue);

        removeFromExpression(currentExpression, Operator.MULTIPLICATION, currentOperandValue);
    }

    private static void appendToExpression(StringBuilder currentExpression, Operator operator, long currentOperand) {
        currentExpression.append(operator.getSymbol())
            .append(currentOperand);
    }

    private static void removeFromExpression(StringBuilder currentExpression, Operator operator, long currentOperand) {
        currentExpression.setLength(currentExpression.length() - operator.getSymbolLength() - String.valueOf(currentOperand)
            .length());
    }

    private enum Operator {
        ADDITION("+"),
        SUBTRACTION("-"),
        MULTIPLICATION("*"),
        NONE("");

        private final String symbol;
        private final int symbolLength;

        Operator(String symbol) {
            this.symbol = symbol;
            this.symbolLength = symbol.length();
        }

        public String getSymbol() {
            return symbol;
        }

        public int getSymbolLength() {
            return symbolLength;
        }
    }

    private static class Equation {

        private final String digits;
        private final int target;

        public Equation(String digits, int target) {
            this.digits = digits;
            this.target = target;
        }

        public String getDigits() {
            return digits;
        }

        public int getTarget() {
            return target;
        }
    }

}
