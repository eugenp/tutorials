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
}
