package com.baeldung.mavenlifecycle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CourseAppUnitTest {

    @Mock
    CourseApp courseApp;

    @Test
    void whenGetCourse_ThenCourseShouldBePresent() {
        when(courseApp.getCourse()).thenReturn("Baeldung Spring Masterclass");
        assertEquals("Baeldung Spring Masterclass", courseApp.getCourse());
    }
}
