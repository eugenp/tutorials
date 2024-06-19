package junitvsmockito;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class JunitVsMockitoUnitTest {

    @Mock NonInstantiableClassForMockito mock;

    @Test
    public void whenUsingJunit_thenObjectCanBeInstantiated() {
        InstantiableClassForJunit testableClass = new InstantiableClassForJunit();

        assertEquals("tested unit", testableClass.testableComponent());
    }

    @Test
    public void whenUsingMockito_thenObjectNeedsToBeMocked() {
        when(mock.nonTestableComponent()).thenReturn("mocked value");

        assertEquals("mocked value", mock.nonTestableComponent());
    }
}
