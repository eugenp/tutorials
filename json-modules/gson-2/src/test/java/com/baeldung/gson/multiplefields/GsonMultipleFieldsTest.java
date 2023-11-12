package com.baeldung.gson.multiplefields;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonMultipleFieldsTest {

    @Test
    public void givenLegacyClassWithMultipleFields_whenSerializingWithGson_thenIllegalArgumentExceptionIsThrown() {
        // Given a class with a class hierarchy that defines multiple fields with the same name
        StudentV2 student = new StudentV2("Henry", "Winter", "123");

        // When serializing using Gson, then an IllegalArgumentException exception is thrown
        Gson gson = new Gson();
        assertThatThrownBy(() -> gson.toJson(student)).isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("declares multiple JSON fields");
    }

    @Test
    public void givenWrapperClassWithMultipleFields_whenSerializingWithGson_thenIllegalArgumentExceptionIsThrown() {
        // Given a class with a class hierarchy that defines multiple fields with the same name
        ImprovedStudent.StudentIdentification id = new ImprovedStudent.StudentIdentification(123L, "Henry", "Winter");
        ImprovedStudent student = new ImprovedStudent(id);

        // When serializing using Gson, then an IllegalArgumentException exception is thrown
        Gson gson = new Gson();
        assertThatThrownBy(() -> gson.toJson(student)).isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("declares multiple JSON fields");
    }

    @Test
    public void
    givenLegacyStudent_whenSerializingWithGsonExclusionStrategy_thenSerializes() {
        // Given a class with a class hierarchy that defines multiple fields with the same name
        StudentV2 student = new StudentV2("Henry", "Winter", "123");

        // When serializing using Gson add an ExclusionStrategy
        Gson gson = new GsonBuilder().setExclusionStrategies(new LegacyStudentExclusionStrategy()).create();

        // Then ensure the student can be serialized, then deserialized back into an equal instance
        assertThat(gson.fromJson(gson.toJson(student), StudentV2.class)).isEqualTo(student);
    }

    @Test
    public void
    givenClassWithMultipleFields_whenSerializingWithGsonExclusionStrategy_thenSerializes() {
        // Given a class with a class hierarchy that defines multiple fields with the same name
        ImprovedStudent.StudentIdentification id = new ImprovedStudent.StudentIdentification(123L, "Henry", "Winter");
        ImprovedStudent student = new ImprovedStudent(id);

        // When serializing using Gson add an ExclusionStrategy
        Gson gson = new GsonBuilder().setExclusionStrategies(new ImprovedStudentExclusionStrategy()).create();

        // Then ensure the student can be serialized, then deserialized back into an equal instance
        assertThat(gson.fromJson(gson.toJson(student), ImprovedStudent.class)).isEqualTo(student);
    }

    @Test
    public void
    givenClassWithMultipleFields_whenSerializingWithGsonExposeAnnotation_thenSerializes() {
        // Given a class with a class hierarchy that defines multiple fields with the same name
        ImprovedStudent.StudentIdentification id = new ImprovedStudent.StudentIdentification(123L, "Henry", "Winter");
        ImprovedStudent student = new ImprovedStudent(id);

        // When serializing using Gson add an ExclusionStrategy
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        // Then ensure the student can be serialized, then deserialized back into an equal instance
        assertThat(gson.fromJson(gson.toJson(student), ImprovedStudent.class)).isEqualTo(student);
    }
}