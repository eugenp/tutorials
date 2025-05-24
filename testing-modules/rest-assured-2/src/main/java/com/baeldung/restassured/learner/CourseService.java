package com.baeldung.restassured.learner;

import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
class CourseService {

    private static final Map<String, Course> COURSE_MAP = new ConcurrentHashMap<>();

    static {
        Course wizardry = new Course("Wizardry");
        COURSE_MAP.put(wizardry.getCode(), wizardry);
    }

    Collection<Course> getCourses() {
        return COURSE_MAP.values();
    }

    Course getCourse(String code) {
        return Optional.ofNullable(COURSE_MAP.get(code)).orElseThrow(() -> new CourseNotFoundException(code));
    }
}
