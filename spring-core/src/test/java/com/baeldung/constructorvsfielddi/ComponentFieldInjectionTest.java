package com.baeldung.constructorvsfielddi;


import com.baeldung.constructorvsfielddi.components.ComponentFieldInjection;
import com.baeldung.constructorvsfielddi.dependencies.AnotherDependency;
import com.baeldung.constructorvsfielddi.dependencies.SimpleDependency;
import org.junit.Test;

import java.lang.reflect.Field;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * @author aiet
 */
public class ComponentFieldInjectionTest {

    @Test
    public void whenSomethingInvoked_thenDependencyMethodsShouldBeInvoked() throws IllegalAccessException {
        SimpleDependency dependency = mock(SimpleDependency.class);
        AnotherDependency anotherDependency = mock(AnotherDependency.class);
        ComponentFieldInjection componentFieldInjection = new ComponentFieldInjection();
        Field[] fields = componentFieldInjection
          .getClass()
          .getDeclaredFields();
        for (Field field : fields) {
            if (field.getType() == SimpleDependency.class) {
                field.setAccessible(true);
                field.set(componentFieldInjection, dependency);
            } else if (field.getType() == AnotherDependency.class) {
                field.setAccessible(true);
                field.set(componentFieldInjection, anotherDependency);
            }
        }
        componentFieldInjection.doSomething();
        verify(dependency).doSimple();
        verify(anotherDependency).doAnother();
    }

}