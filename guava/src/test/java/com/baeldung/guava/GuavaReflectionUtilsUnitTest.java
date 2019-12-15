package com.baeldung.guava;


import com.google.common.collect.Lists;
import com.google.common.reflect.Invokable;
import com.google.common.reflect.TypeToken;
import org.junit.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GuavaReflectionUtilsUnitTest {

    @Test
    public void givenTwoGenericList_whenCheckIsAssignableFrom_thenReturnTrueDueToTypeErasure() {
        //given
        ArrayList<String> stringList = Lists.newArrayList();
        ArrayList<Integer> intList = Lists.newArrayList();

        //when
        boolean result = stringList.getClass().isAssignableFrom(intList.getClass());

        //then
        assertTrue(result);
    }

    @Test
    public void givenTypeToken_whenResolveType_thenShouldResolveProperType() {
        //given
        TypeToken<List<String>> stringListToken = new TypeToken<List<String>>() {
        };
        TypeToken<List<Integer>> integerListToken = new TypeToken<List<Integer>>() {
        };
        TypeToken<List<? extends Number>> numberTypeToken = new TypeToken<List<? extends Number>>() {
        };

        //then
        assertFalse(stringListToken.isSubtypeOf(integerListToken));
        assertFalse(numberTypeToken.isSubtypeOf(integerListToken));
        assertTrue(integerListToken.isSubtypeOf(numberTypeToken));
    }

    @Test
    public void givenCustomClass_whenCaptureGeneric_thenReturnTypeAtRuntime() {
        //given
        ParametrizedClass<String> parametrizedClass = new ParametrizedClass<String>() {
        };

        //then
        assertEquals(parametrizedClass.type, TypeToken.of(String.class));
    }

    @Test
    public void givenComplexType_whenGetTypeArgument_thenShouldReturnTypeAtRuntime() {
        //given
        TypeToken<Function<Integer, String>> funToken = new TypeToken<Function<Integer, String>>() {
        };

        //when
        TypeToken<?> funResultToken = funToken.resolveType(Function.class.getTypeParameters()[1]);

        //then
        assertEquals(funResultToken, TypeToken.of(String.class));
    }


    @Test
    public void givenMapType_whenGetTypeArgumentOfEntry_thenShouldReturnTypeAtRuntime() throws NoSuchMethodException {
        //given
        TypeToken<Map<String, Integer>> mapToken = new TypeToken<Map<String, Integer>>() {
        };

        //when
        TypeToken<?> entrySetToken = mapToken.resolveType(Map.class.getMethod("entrySet").getGenericReturnType());

        //then
        assertEquals(entrySetToken, new TypeToken<Set<Map.Entry<String, Integer>>>() {
        });
    }

    @Test
    public void givenInvokable_whenCheckPublicMethod_shouldReturnTrue() throws NoSuchMethodException {
        //given
        Method method = CustomClass.class.getMethod("somePublicMethod");
        Invokable<CustomClass, ?> invokable = new TypeToken<CustomClass>() {
        }.method(method);

        //when
        boolean isPublicStandradJava = Modifier.isPublic(method.getModifiers());
        boolean isPublicGuava = invokable.isPublic();
        //then
        assertTrue(isPublicStandradJava);
        assertTrue(isPublicGuava);
    }

    @Test
    public void givenInvokable_whenCheckFinalMethod_shouldReturnFalseForIsOverridable() throws NoSuchMethodException {
        //given
        Method method = CustomClass.class.getMethod("notOverridablePublicMethod");
        Invokable<CustomClass, ?> invokable = new TypeToken<CustomClass>() {
        }.method(method);

        //when
        boolean isOverridableStandardJava = (!(Modifier.isFinal(method.getModifiers()) || Modifier.isPrivate(method.getModifiers())
                || Modifier.isStatic(method.getModifiers())
                || Modifier.isFinal(method.getDeclaringClass().getModifiers())));
        boolean isOverridableFinalGauava = invokable.isOverridable();

        //then
        assertFalse(isOverridableStandardJava);
        assertFalse(isOverridableFinalGauava);
    }

    @Test
    public void givenListOfType_whenGetReturnRype_shouldCaptureTypeAtRuntime() throws NoSuchMethodException {
        //given
        Method getMethod = List.class.getMethod("get", int.class);

        //when
        Invokable<List<Integer>, ?> invokable = new TypeToken<List<Integer>>() {
        }.method(getMethod);

        //then
        assertEquals(TypeToken.of(Integer.class), invokable.getReturnType()); // Not Object.class!
    }


    abstract class ParametrizedClass<T> {
        TypeToken<T> type = new TypeToken<T>(getClass()) {
        };
    }

    class CustomClass {
        public void somePublicMethod() {
        }

        public final void notOverridablePublicMethod() {

        }
    }
}
