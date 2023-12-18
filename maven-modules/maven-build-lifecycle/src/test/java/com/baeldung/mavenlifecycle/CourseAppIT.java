package com.baeldung.mavenlifecycle;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class CourseAppIT {

    @Test
    void givenIntegrationTest_whenGetCourse_ThenCourseShouldBePresent() {
        CourseApp courseApp = new CourseApp();

        assertEquals("Baeldung Spring Masterclass", courseApp.getCourse());
    }
}
