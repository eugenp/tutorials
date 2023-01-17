package com.baeldung.triple;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

public class TripleInListUnitTest {

    enum OP {
        PLUS("+"), MINUS("-"), MULTIPLY("x");
        final String opSign;

        OP(String x) {
            this.opSign = x;
        }
    }

    private String createQuestion(Long num1, OP operator, Long num2) {
        long result;
        switch (operator) {
            case PLUS:
                result = num1 + num2;
                break;
            case MINUS:
                result = num1 - num2;
                break;
            case MULTIPLY:
                result = num1 * num2;
                break;
            default:
                throw new IllegalArgumentException("Unknown operator");
        }
        return String.format("%d %s %d = ? ( answer: %d )", num1, operator.opSign, num2, result);
    }

    private static final List<String> EXPECTED_QUESTIONS = Arrays.asList(
        "100 - 42 = ? ( answer: 58 )",
        "100 + 42 = ? ( answer: 142 )",
        "100 x 42 = ? ( answer: 4200 )");

    @Test
    void givenTripleValues_whenStoreAsList_thenTypeIsNotSafe() {

        List myTriple1 = new ArrayList(3);
        myTriple1.add(100L);
        myTriple1.add(OP.MINUS);
        myTriple1.add(42L);

        List myTriple2 = new ArrayList(3);
        myTriple2.add(100L);
        myTriple2.add(OP.PLUS);
        myTriple2.add(42L);

        List myTriple3 = new ArrayList(3);
        myTriple3.add(100L);
        myTriple3.add(OP.MULTIPLY);
        myTriple3.add(42L);

        List<List> listOfTriples = new ArrayList<>(Arrays.asList(myTriple1, myTriple2, myTriple3));

        List oopsTriple = new ArrayList(3);
        oopsTriple.add("Oops");
        oopsTriple.add(911L);
        oopsTriple.add("The type is wrong");

        listOfTriples.add(oopsTriple);
        assertEquals(4, listOfTriples.size());

        List<String> questions = listOfTriples.stream()
            .filter(
                triple -> triple.size() == 3
                    && triple.get(0) instanceof Long
                    && triple.get(1) instanceof OP
                    && triple.get(2) instanceof Long
            ).map(triple -> {
                Long left = (Long) triple.get(0);
                OP op = (OP) triple.get(1);
                Long right = (Long) triple.get(2);
                return createQuestion(left, op, right);
            }).collect(Collectors.toList());

        assertEquals(EXPECTED_QUESTIONS, questions);
    }

    @Test
    void givenTripleValues_whenUsingTheTripleClass_thenTypeIsSafeAndNeat() {
        Triple<Long, OP, Long> triple1 = new Triple<>(100L, OP.MINUS, 42L);
        Triple<Long, OP, Long> triple2 = new Triple<>(100L, OP.PLUS, 42L);
        Triple<Long, OP, Long> triple3 = new Triple<>(100L, OP.MULTIPLY, 42L);
        Triple<String, Long, String> tripleOops = new Triple<>("Oops", 911L, "The type is wrong");

        List<Triple<Long, OP, Long>> listOfTriples = new ArrayList<>(Arrays.asList(triple1, triple2, triple3));
        // listOfTriples.add(tripleOops); // Compiler error: "java: incompatible types ... "

        List<String> questions = listOfTriples.stream()
            .map(triple -> createQuestion(triple.getLeft(), triple.getMiddle(), triple.getRight()))
            .collect(Collectors.toList());

        assertEquals(EXPECTED_QUESTIONS, questions);
    }
}
