package com.baeldung.jackson.serialization.jsonvalue;

import com.baeldung.jackson.domain.Author;
import com.baeldung.jackson.domain.Item;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.List;

/**
 * Source code github.com/readlearncode
 *
 * @author Alex Theedom www.readlearncode.com
 * @version 1.0
 */
public class Course extends Item {

    public enum Medium {
        CLASSROOM, ONLINE
    }

    public enum Level {
        BEGINNER("Beginner", 1), INTERMEDIATE("Intermediate", 2), ADVANCED("Advanced", 3);

        private String name;
        private int number;

        Level(String name, int number) {
            this.name = name;
            this.number = number;
        }

        @JsonValue
        public String getName() {
            return name;
        }
    }

    private float duration;
    private Medium medium;
    private Level level;
    private List<Course> prerequisite;

    public Course(String title, Author author) {
        super(title, author);
    }

    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    public Medium getMedium() {
        return medium;
    }

    public void setMedium(Medium medium) {
        this.medium = medium;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public List<Course> getPrerequisite() {
        return prerequisite;
    }

    public void setPrerequisite(List<Course> prerequisite) {
        this.prerequisite = prerequisite;
    }
}
