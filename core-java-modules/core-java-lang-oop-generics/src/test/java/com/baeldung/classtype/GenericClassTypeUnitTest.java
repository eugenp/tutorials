package com.baeldung.classtype;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.junit.jupiter.api.Test;


public class GenericClassTypeUnitTest {

    @Test
    public void givenContainerClassWithGenericType_whenTypeParameterUsed_thenReturnsClassType() {
        var stringContainer = new ContainerTypeFromTypeParameter<>(String.class);
        Class<String> containerClass = stringContainer.getClazz();

        assertEquals(containerClass, String.class);
    }

    @Test
    public void givenContainerClassWithGenericType_whenReflectionUsed_thenReturnsClassType() {
        var stringContainer = new ContainerTypeFromReflection<>("Hello Java");
        Class<?> stringClazz = stringContainer.getClazz();
        assertEquals(stringClazz, String.class);

        var integerContainer = new ContainerTypeFromReflection<>(1);
        Class<?> integerClazz = integerContainer.getClazz();
        assertEquals(integerClazz, Integer.class);
    }

    @Test
    public void giveContainerClassWithGenericType_whenTypeTokenUsed_thenReturnsClassType() {
        class ContainerTypeFromTypeToken extends TypeToken<List<String>> {
        }

        var container = new ContainerTypeFromTypeToken();
        ParameterizedType type = (ParameterizedType) container.getType();
        Type actualTypeArgument = type.getActualTypeArguments()[0];

        assertEquals(actualTypeArgument, String.class);
    }
}
