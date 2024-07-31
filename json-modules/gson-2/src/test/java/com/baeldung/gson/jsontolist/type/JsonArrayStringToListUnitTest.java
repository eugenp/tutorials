package com.baeldung.gson.jsontolist.type;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.reflect.TypeParameter;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

public class JsonArrayStringToListUnitTest {

    Logger LOGGER = LoggerFactory.getLogger(JsonArrayStringToListUnitTest.class);
    final String jsonArrayOfStudents =
        "["
            + "{\"name\":\"John\", \"grade\":\"1\"}, "
            + "{\"name\":\"Tom\", \"grade\":\"2\"}, "
            + "{\"name\":\"Ram\", \"grade\":\"3\"}, "
            + "{\"name\":\"Sara\", \"grade\":\"1\"}"
      + "]";
    final String jsonArrayOfSchools =
        "["
            + "{\"name\":\"St. John\", \"city\":\"Chicago City\"}, "
            + "{\"name\":\"St. Tom\", \"city\":\"New York City\"}, "
            + "{\"name\":\"St. Ram\", \"city\":\"Mumbai\"}, "
            + "{\"name\":\"St. Sara\", \"city\":\"Budapest\"}"
      + "]";

    @Test
    void givenJsonArray_whenListElementTypeDynamic_thenConvertToJavaListUsingTypeToken() {
        Gson gson = new Gson();
        TypeToken<List<Student>> typeTokenForListOfStudents = new TypeToken<List<Student>>(){};
        TypeToken<List<School>> typeTokenForListOfSchools = new TypeToken<List<School>>(){};
        List<Student> studentsLst = gson.fromJson(jsonArrayOfStudents, typeTokenForListOfStudents.getType());
        List<School> schoolLst = gson.fromJson(jsonArrayOfSchools, typeTokenForListOfSchools.getType());
        assertAll(
            () -> studentsLst.forEach(e -> assertTrue(e instanceof Student)),
            () -> schoolLst.forEach(e -> assertTrue(e instanceof School))
        );
    }



    @Test
    void givenJsonArray_whenListElementTypeDynamic_thenConvertToJavaListUsingTypeTokenFails() {
        Gson gson = new Gson();
        assertThrows(IllegalArgumentException.class, () -> gson.fromJson(jsonArrayOfStudents, new ListWithDynamicTypeElement<Student>().getType()));
    }

    class ListWithDynamicTypeElement<T> {
        Type getType() {
            TypeToken<List<T>> typeToken = new TypeToken<List<T>>(){};
            return typeToken.getType();
        }
    }

    @Test
    void givenJsonArray_whenListElementTypeDynamic_thenConvertToJavaListUsingGetParameterized() {
        Gson gson = new Gson();
        List<Student> studentsLst = gson.fromJson(jsonArrayOfStudents, getGenericTypeForListFromTypeTokenUsingGetParameterized(Student.class));
        List<School> schoolLst = gson.fromJson(jsonArrayOfSchools, getGenericTypeForListFromTypeTokenUsingGetParameterized(School.class));
        assertAll(
            () -> studentsLst.forEach(e -> assertTrue(e instanceof Student)),
            () -> schoolLst.forEach(e -> assertTrue(e instanceof School))
        );
    }

    @Test
    void givenJsonArray_whenListElementTypeDynamic_thenConvertToJavaListUsingJsonArray() {
        List<Student> studentsLst = createListFromJsonArray(jsonArrayOfStudents, Student.class);
        List<School> schoolLst = createListFromJsonArray(jsonArrayOfSchools, School.class);
        assertAll(
            () -> studentsLst.forEach(e -> assertTrue(e instanceof Student)),
            () -> schoolLst.forEach(e -> assertTrue(e instanceof School))
        );
    }

    @Test
    void givenJsonArray_whenListElementTypeDynamic_thenConvertToJavaListUsingTypeTokenFromGuava() {
        Gson gson = new Gson();
        List<Student> studentsLst = gson.fromJson(jsonArrayOfStudents, getTypeForListUsingTypeTokenFromGuava(Student.class));
        List<School> schoolLst = gson.fromJson(jsonArrayOfSchools, getTypeForListUsingTypeTokenFromGuava(School.class));
        assertAll(
            () -> studentsLst.forEach(e -> assertTrue(e instanceof Student)),
            () -> schoolLst.forEach(e -> assertTrue(e instanceof School))
        );
    }

    @Test
    void givenJsonArray_whenListElementTypeDynamic_thenConvertToJavaListUsingParameterizedType() {
        Gson gson = new Gson();
        List<Student> studentsLst = gson.fromJson(jsonArrayOfStudents, ParameterizedTypeImpl.make(List.class, Student.class));
        List<School> schoolLst = gson.fromJson(jsonArrayOfSchools, ParameterizedTypeImpl.make(List.class, School.class));
        assertAll(
            () -> studentsLst.forEach(e -> assertTrue(e instanceof Student)),
            () -> schoolLst.forEach(e -> assertTrue(e instanceof School))
        );
    }

    Type getGenericTypeForListFromTypeTokenUsingGetParameterized(Class elementClass) {
        return TypeToken.getParameterized(List.class, elementClass).getType();
    }

    <T> List<T> createListFromJsonArray(String jsonArray, Type elementType) {
        Gson gson = new Gson();
        List<T> list = new ArrayList<>();
        JsonArray array = gson.fromJson(jsonArray, JsonArray.class);

        for(JsonElement element : array) {
            T item = gson.fromJson(element, elementType);
            list.add(item);
        }
        return list;
    }

    <T> Type getTypeForListUsingTypeTokenFromGuava(Class<T> type) {
        return new com.google.common.reflect.TypeToken<List<T>>() {}
            .where(new TypeParameter<T>() {}, type)
            .getType();
    }

}
