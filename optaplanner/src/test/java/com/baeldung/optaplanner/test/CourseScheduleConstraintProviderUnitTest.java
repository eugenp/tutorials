package com.baeldung.optaplanner.test;

import com.baeldung.optaplanner.CourseSchedule;
import com.baeldung.optaplanner.CourseScheduleConstraintProvider;
import com.baeldung.optaplanner.Lecture;
import org.junit.jupiter.api.Test;
import org.optaplanner.test.api.score.stream.ConstraintVerifier;

public class CourseScheduleConstraintProviderUnitTest {

    private final ConstraintVerifier<CourseScheduleConstraintProvider, CourseSchedule> constraintVerifier =
            ConstraintVerifier.build(new CourseScheduleConstraintProvider(), CourseSchedule.class, Lecture.class);

    @Test
    public void testCourseScheduleConstraint() {

        Lecture lecture = new Lecture(0L);
        lecture.setPeriod(1);
        lecture.setRoomNumber(1);
        Lecture conflictedLecture = new Lecture(1L);
        conflictedLecture.setPeriod(1);
        conflictedLecture.setRoomNumber(1);

        constraintVerifier.verifyThat(CourseScheduleConstraintProvider::roomConflict)
                .given(lecture, conflictedLecture)
                .penalizesBy(1);
    }
}
