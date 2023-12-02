package com.baeldung.gson.multiplefields;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonMultipleFieldsUnitTest {

    @Test
    public void givenBasicStudent_whenSerializingWithGson_thenTransientFieldNotSet() {
        // Given a class with a transient field
        BasicStudent student = new BasicStudent("Henry Winter", "Greek Studies", "Classical Greek Studies");

        // When serializing using Gson
        Gson gson = new Gson();
        String json = gson.toJson(student);

        // Then the deserialized instance doesn't contain the transient field value
        BasicStudent deserialized = gson.fromJson(json, BasicStudent.class);
        assertThat(deserialized.getMajor()).isNull();
    }

    @Test
    public void givenStudentV2_whenSerializingWithGson_thenIllegalArgumentExceptionIsThrown() {
        // Given a class with a class hierarchy that defines multiple fields with the same name
        StudentV2 student = new StudentV2("Henry", "Winter", "Greek Studies");

        // When serializing using Gson, then an IllegalArgumentException exception is thrown
        Gson gson = new Gson();
        assertThatThrownBy(() -> gson.toJson(student)).isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("declares multiple JSON fields named 'firstName'");
    }

    @Test
    public void givenStudentV2_whenSerializingWithGsonExposeAnnotation_thenSerializes() {
        // Given a class with a class hierarchy that defines multiple fields with the same name
        StudentV2 student = new StudentV2("Henry", "Winter", "Greek Studies");

        // When serializing using Gson exclude fields without @Expose
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
            .create();

        // Then ensure the student can be serialized, then deserialized back into an equal instance
        String json = gson.toJson(student);
        assertThat(gson.fromJson(json, StudentV2.class)).isEqualTo(student);
    }

    @Test
    public void givenStudentV2_whenSerializingWithGsonExclusionStrategy_thenSerializes() {
        // Given a class with a class hierarchy that defines multiple fields with the same name
        StudentV2 student = new StudentV2("Henry", "Winter", "Greek Studies");

        // When serializing using Gson add an ExclusionStrategy
        Gson gson = new GsonBuilder().setExclusionStrategies(new StudentExclusionStrategy())
            .create();

        // Then ensure the student can be serialized, then deserialized back into an equal instance
        assertThat(gson.fromJson(gson.toJson(student), StudentV2.class)).isEqualTo(student);
    }

}