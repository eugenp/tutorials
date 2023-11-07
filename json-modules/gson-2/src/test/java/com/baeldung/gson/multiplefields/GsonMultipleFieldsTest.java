package com.baeldung.gson.multiplefields;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonMultipleFieldsTest {


    @Test
    public void givenClassWithMultipleFields_whenSerializingWithGson_thenIllegalArgumentExceptionIsThrown() {
        // Given a class with a class hierarchy that defines multiple fields with the same name
        ImprovedStudent.StudentIdentification id = new ImprovedStudent.StudentIdentification(123L, "Henry", "Winter");
        ImprovedStudent student = new ImprovedStudent(id, "Greek Studies");

        // When serializing using Gson, then an IllegalArgumentException exception is thrown
        Gson gson = new Gson();
        assertThatThrownBy(() -> gson.toJson(student)).isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("declares multiple JSON fields");
    }

    @Test
    public void
    givenClassWithMultipleFields_whenSerializingWithGsonExclusionStrategy_thenSerializes() {
        // Given a class with a class hierarchy that defines multiple fields with the same name
        ImprovedStudent.StudentIdentification id = new ImprovedStudent.StudentIdentification(123L, "Henry", "Winter");
        ImprovedStudent student = new ImprovedStudent(id,"Greek Studies");

        // When serializing using Gson add an ExclusionStrategy
        Gson gson = new GsonBuilder().setExclusionStrategies(new StudentExclusionStrategy()).create();
        System.out.println(gson.toJson(student));
    }

    @Test
    public void
    givenClassWithMultipleFields_whenSerializingWithGsonExposeAnnotation_thenSerializes() {
        // Given a class with a class hierarchy that defines multiple fields with the same name
        ImprovedStudent.StudentIdentification id = new ImprovedStudent.StudentIdentification(123L, "Henry", "Winter");
        ImprovedStudent student = new ImprovedStudent(id,"Greek Studies");

        // When serializing using Gson add an ExclusionStrategy
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        System.out.println(gson.toJson(student));
    }
}
