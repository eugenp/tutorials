package com.baeldung.properties.multipleyaml;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

import java.util.List;

@Configuration
@ConfigurationProperties
@PropertySources({
        @PropertySource(value = "classpath:application-teachers.yml", factory = MultipleYamlPropertySourceFactory.class),
        @PropertySource(value = "classpath:application-students.yml", factory = MultipleYamlPropertySourceFactory.class)})
public class MultipleYamlConfiguration {

    List<String> teachers;
    List<String> students;

    public void setTeachers(List<String> teachers) {
        this.teachers = teachers;
    }

    public void setStudents(List<String> students) {
        this.students = students;
    }

    public List<String> getTeachers() {
        return teachers;
    }

    public List<String> getStudents() {
        return students;
    }
}