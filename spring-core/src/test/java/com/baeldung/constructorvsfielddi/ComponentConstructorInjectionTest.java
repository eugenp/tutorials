package com.baeldung.constructorvsfielddi;

import com.baeldung.constructorvsfielddi.components.ComponentConstructorInjection;
import com.baeldung.constructorvsfielddi.dependencies.AnotherDependency;
import com.baeldung.constructorvsfielddi.dependencies.SimpleDependency;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * @author aiet
 */
public class ComponentConstructorInjectionTest {

    @Test
    public void whenSomethingInvoked_thenDependencyMethodsShouldBeInvoked() {
        SimpleDependency dependency = mock(SimpleDependency.class);
        AnotherDependency anotherDependency = mock(AnotherDependency.class);
        ComponentConstructorInjection componentConstructorInjection = new ComponentConstructorInjection(anotherDependency, dependency);
        componentConstructorInjection.doSomething();
        verify(dependency).doSimple();
        verify(anotherDependency).doAnother();
    }

}