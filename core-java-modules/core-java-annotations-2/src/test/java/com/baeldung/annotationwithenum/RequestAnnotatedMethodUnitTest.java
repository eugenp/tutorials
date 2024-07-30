package com.baeldung.annotationwithenum;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import com.baeldung.annotationwithenum.DataController;
import com.baeldung.annotationwithenum.ExtendedRequestContentType;

public class RequestAnnotatedMethodUnitTest {

    @Test
    public void whenDataControllerCalledThenNoErrorThrown(){
        DataController ctrl = new DataController();

        assertEquals("CREATED", ctrl.createData());
        assertEquals("UPDATED", ctrl.updateData());
    }
    @Test
    public void whenEnumAndConstantsAreInSyncThenOk() {
        Set<String> enumValues = getEnumNames();
        List<String> constantValues = getConstantValues();
        Set<String> uniqueConstantValues = constantValues.stream().distinct().collect(Collectors.toSet());

        assertEquals(constantValues.size(), uniqueConstantValues.size());
        assertEquals(enumValues, uniqueConstantValues);
    }

    private Set<String> getEnumNames() {
        Set<String> enumValues = new HashSet<>();
        for (ExtendedRequestContentType contentType : ExtendedRequestContentType.values()) {
            enumValues.add(contentType.name());
        }
        return enumValues;
    }

    private List<String> getConstantValues() {
        List<String> constantValues = new ArrayList<>();
        try {
            Field[] fields = ExtendedRequestContentType.Constants.class.getDeclaredFields();
            for (Field field : fields) {
                if (field.getType() == String.class && Modifier.isPublic(field.getModifiers())) {
                    field.setAccessible(true);
                   constantValues.add((String) field.get(null));
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return constantValues;
    }
}
