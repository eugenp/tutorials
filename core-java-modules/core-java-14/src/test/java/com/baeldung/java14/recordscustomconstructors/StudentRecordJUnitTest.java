package com.baeldung.java14.recordscustomconstructors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class StudentRecordJUnitTest {

    @Test
    void givenStudentRecordData_whenCreated_thenStudentPropertiesMatch() {
        StudentRecord s1 = new StudentRecord("John", 1, 90);
        StudentRecord s2 = new StudentRecord("Jane", 2, 80);
        assertEquals("John", s1.name());
        assertEquals(1, s1.rollNo());
        assertEquals(90, s1.marks());
        assertEquals("Jane", s2.name());
        assertEquals(2, s2.rollNo());
        assertEquals(80, s2.marks());
    }

    @Test
    void givenStudentRecordsList_whenSortingDataWithName_thenStudentsSorted() {
        List<StudentRecord> studentRecords = new ArrayList<>(List.of(new StudentRecord("Dana", 1, 85), new StudentRecord("Jim", 2, 90), new StudentRecord("Jane", 3, 80)));
        studentRecords.sort(Comparator.comparing(StudentRecord::name));

        assertEquals("Jane", studentRecords.get(1).name());
    }

    @Test
    void whenCreateStudentRecordWithInvalidMarks_thenRaiseException() {
        assertThrows(IllegalArgumentException.class, () -> new StudentRecord("Jane", 2, 150));
    }

    @Test
    void whenCreateStudentRecordV2_thenCorrect() {
        StudentRecordV2 studentV2 = new StudentRecordV2("Jane", 2, 85);
        assertEquals('B', studentV2.grade());
    }

    @Test
    void whenCreateStudentRecordV3_thenCorrect() {
        StudentRecordV3 studentV3 = new StudentRecordV3("Jane");

        assertThat(studentV3.id()).isEmpty();
        assertThat(studentV3.hobbies()).isEmpty();
        assertTrue(studentV3.active());
    }

    @Test
    void whenCreateUserPreference_thenCorrect() {
        UserPreference defaultOne = UserPreference.DEFAULT;
        assertFalse(defaultOne.superUser());
        assertEquals(Map.of("language", "EN", "timezone", "UTC"), defaultOne.preferences());

        UserPreference defaultTwo = UserPreference.DEFAULT;
        assertSame(defaultOne, defaultTwo);
    }
}