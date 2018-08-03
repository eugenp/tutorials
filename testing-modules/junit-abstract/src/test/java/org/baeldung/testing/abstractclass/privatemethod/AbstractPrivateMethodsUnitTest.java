/**
 *
 */
package org.baeldung.testing.abstractclass.privatemethod;

import java.time.LocalDateTime;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * Providing custom values for private methods using powermock
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(AbstractPrivateMethods.class)
public class AbstractPrivateMethodsUnitTest {

    @Test
    public void givenNonAbstractMethodAndCallPrivateMethod_whenMockPrivateMethod_thenVerifyBehaviour() throws Exception {
        AbstractPrivateMethods mockClass = PowerMockito.mock(AbstractPrivateMethods.class);

        String dateTime = LocalDateTime
          .now()
          .toString();
        PowerMockito
          .doCallRealMethod()
          .when(mockClass)
          .defaultImpl();
        PowerMockito
          .doReturn(dateTime)
          .when(mockClass, "getCurrentDateTime");// .thenReturn(dateTime);
        String actual = mockClass.defaultImpl();
        Assertions.assertEquals(dateTime + "DEFAULT-1", actual);
    }

}
