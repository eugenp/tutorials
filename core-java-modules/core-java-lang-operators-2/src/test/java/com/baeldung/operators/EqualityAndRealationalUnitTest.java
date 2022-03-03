package com.baeldung.operators;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

class EqualityAndRealationalUnitTest {

    @Test
    void whenUseTheOperatorGreaterThanorEqualTo_thenSuccess() {
        int testGrade = 10;
        String result = "";
        if (testGrade >= 7) {
            result = "Approved Student!";
        } else {
            result = "Failed Student!";
        }
        assertEquals(result, "Approved Student!");
    }

    @Test
    void whenUseTheOperatorEqualTo_thenSuccess() {
        int grade = 3;
        String result = "";
        if (grade == 3) {
            result = "The guest is VIP";
        } else {
            result = "The guest isn't VIP";
        }
        assertEquals(result, "The guest is VIP");
    }

    @Test
    void whenUseTheOperatorNotEqualTo_thenSuccess() {
        int grade = 2;
        String result = "";
        if (grade != 3) {
            result = "The guest isn't VIP";
        } else {
            result = "The guest is VIP";
        }
        assertEquals(result, "The guest isn't VIP");
    }

    @Test
    void whenUseTheOperatorGreaterThan_thenSuccess() {
        int grade = 3;
        String result = "";

        if (grade > 2) {
            result = "The guest is VIP";
        } else {
            result = "The guest isn't VIP";
        }
        assertEquals(result, "The guest is VIP");
    }

    @Test
    void whenUseTheOperatorLessThan_thenSuccess() {
        int grade = 3;
        String result = "";
        if (grade <= 3) {
            result = "The guest is VIP";
        } else {
            result = "The guest isn't VIP";
        }
        assertEquals(result, "The guest is VIP");
    }

    @Test
    void whenUseTheOperatorLessThanOrEqualTo_thenSuccess() {
        int grade = 2;
        String result = "";
        if (grade <= 2) {
            result = "The guest isn't VIP";
        } else {
            result = "The guest is VIP";
        }
        assertEquals(result, "The guest isn't VIP");
    }
}