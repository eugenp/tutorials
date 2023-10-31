package returnfirstnonull;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.apache.commons.lang3.ObjectUtils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ReturnFirstNonNullLazyEvaluateUnitTest {

    private static Object METHOD_B_OBJECT = new Object();

    private Object methodA() { return null; }

    private Object methodB() { return METHOD_B_OBJECT; }

    private Object methodC() { return null; }

    @Test
    public void givenChainOfMethods_thenLazilyEvaluateMethodsUntilFirstNonNull() {
        Object object = methodA();
        if(object == null)
            object = methodB();

        if(object == null)
            object = methodC();

        assertTrue(object != null);
        assertEquals(METHOD_B_OBJECT, object);  
    }

    @Test
    public void givenChainOfMethods_whenUsingSupplierInterface_thenLazilyEvaluateMethodsUntilFirstNonNull() {
        Optional<Object> object = Stream
            .<Supplier<Object>>of(
                this::methodA, 
                this::methodB,
                this::methodC)
            .map(Supplier::get)
            .filter(Objects::nonNull)
            .findFirst();

        assertTrue(object.isPresent());
        assertEquals(METHOD_B_OBJECT, object.get());
    }

    @Test
    public void givenNullableObjectAndFallbackMethod_whenUsingApacheCommonsLang3_thenReturnFirstNonNull() {
        Object object1 = null;
        Object object = ObjectUtils.getIfNull(object1, this::methodB);

        assertTrue(object != null);
        assertEquals(METHOD_B_OBJECT, object);
    }

    @Test
    public void givenChainOfMethods_whenUsingApacheCommonsLang3_thenReturnFirstNonNull() {
        Object object = ObjectUtils.getFirstNonNull(this::methodA, this::methodB, this::methodC);

        assertTrue(object != null);
        assertEquals(METHOD_B_OBJECT, object);
    }
}