package com.baeldung.staticvariables;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Field;

import org.junit.jupiter.api.Test;

public class StaticVariableUnitTest {

    @Test
    public void initializeStaticVariable_checkAssignedValues() {

        try {
            Class<?> staticVariableDemo = this.getClass()
                .getClassLoader()
                .loadClass("com.baeldung.staticvariables.StaticVariableDemo");

            Field field1 = staticVariableDemo.getField("i");

            assertThat(field1.getInt(staticVariableDemo)).isEqualTo(0);

            Field field2 = staticVariableDemo.getField("j");

            assertThat(field2.getInt(staticVariableDemo)).isEqualTo(20);

        } catch (ClassNotFoundException | NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void initializeStaticVariable_checkStaticBlock() {

        try {
            Class<?> staticVariableDemo = this.getClass()
                .getClassLoader()
                .loadClass("com.baeldung.staticvariables.StaticVariableDemo");

            Field field1 = staticVariableDemo.getField("z");

            assertThat(field1.getInt(staticVariableDemo)).isEqualTo(30);

            Field field2 = staticVariableDemo.getField("a");

            assertThat(field2.getInt(staticVariableDemo)).isEqualTo(50);

        } catch (ClassNotFoundException | NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void initializeStaticVariable_checkFinalValues() {

        try {
            Class<?> staticVariableDemo = this.getClass()
                .getClassLoader()
                .loadClass("com.baeldung.staticvariables.StaticVariableDemo");

            Field field1 = staticVariableDemo.getField("b");

            assertThat(field1.getInt(staticVariableDemo)).isEqualTo(100);

        } catch (ClassNotFoundException | NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void initializeStaticVariable_checkInnerClassValues() {

        try {
            Class<?> staticVariableDemo = this.getClass()
                .getClassLoader()
                .loadClass("com.baeldung.staticvariables.StaticVariableDemo");

            Class<?>[] nestedClasses = staticVariableDemo.getClasses();

            for (Class<?> nestedClass : nestedClasses) {
                if (nestedClass.getName()
                    .equals("Nested")) {

                    Field field1 = nestedClass.getField("nestedClassStaticVariable");
                    assertThat(field1.get(nestedClass)).isEqualTo("test");
                }
            }

        } catch (ClassNotFoundException | NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }
}
