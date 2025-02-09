package com.baeldung.switchstatement;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SwitchStatementUnitTest {
    private final SwitchStatement gradeAssignment = new SwitchStatement();

    @Test
    public void givenVariousScores_whenAssignGradeUsingIfElse_thenCorrectGradesAssigned() {
        assertEquals("Grade: A", gradeAssignment.assignGradeUsingIfElse(95));
        assertEquals("Grade: B", gradeAssignment.assignGradeUsingIfElse(85));
        assertEquals("Grade: C", gradeAssignment.assignGradeUsingIfElse(75));
        assertEquals("Grade: D", gradeAssignment.assignGradeUsingIfElse(65));
        assertEquals("Grade: F", gradeAssignment.assignGradeUsingIfElse(55));
    }

    // --- Grouped Tests for Switch with Integer Division Approach ---
    @Test
    public void givenVariousScores_whenAssignGradeUsingSwitchWithDivision_thenCorrectGradesAssigned() {
        assertEquals("Grade: A", gradeAssignment.assignGradeUsingRangesWithIntegerDivision(95));
        assertEquals("Grade: B", gradeAssignment.assignGradeUsingRangesWithIntegerDivision(85));
        assertEquals("Grade: C", gradeAssignment.assignGradeUsingRangesWithIntegerDivision(75));
        assertEquals("Grade: D", gradeAssignment.assignGradeUsingRangesWithIntegerDivision(65));
        assertEquals("Grade: F", gradeAssignment.assignGradeUsingRangesWithIntegerDivision(55));
    }
}
