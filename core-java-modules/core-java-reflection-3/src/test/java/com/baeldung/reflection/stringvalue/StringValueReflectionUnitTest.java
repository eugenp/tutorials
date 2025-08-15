package com.baeldung.reflection.stringvalue;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Field;
import java.util.Objects;

import org.junit.jupiter.api.Test;

public class StringValueReflectionUnitTest {

    @Test
    void givenPublicStringField_whenAccessWithReflection_thenReturnValue() throws Exception {
        PublicFieldDemo example = new PublicFieldDemo();
        Field field = PublicFieldDemo.class.getField("name");
        String value = (String) field.get(example);
        assertEquals("Baeldung", value);
    }

    @Test
    void givenPrivateStringField_whenAccessWithReflection_thenReturnValue() throws Exception {
        PrivateFieldDemo example = new PrivateFieldDemo();
        Field field = PrivateFieldDemo.class.getDeclaredField("secret");
        field.setAccessible(true);
        String value = (String) field.get(example);
        assertEquals("Hidden Value", value);
    }

    @Test
    void givenFieldNameVariable_whenAccessWithReflection_thenReturnValue() throws Exception {
        DynamicFieldDemo example = new DynamicFieldDemo();
        String fieldName = "title";
        Field field = DynamicFieldDemo.class.getField(fieldName);
        String value = (String) field.get(example);
        assertEquals("Dynamic Access", value);
    }

    public static String getFieldValueAsString(Object obj, String fieldName) throws Exception {
        Field field = obj.getClass()
            .getDeclaredField(fieldName);
        field.setAccessible(true);
        return Objects.toString(field.get(obj), null);
    }

    @Test
    void givenObjectAndFieldName_whenUseUtilityMethod_thenReturnStringValue() throws Exception {
        User user = new User();
        String value = getFieldValueAsString(user, "username");
        assertEquals("baeldung_user", value);
    }
}
