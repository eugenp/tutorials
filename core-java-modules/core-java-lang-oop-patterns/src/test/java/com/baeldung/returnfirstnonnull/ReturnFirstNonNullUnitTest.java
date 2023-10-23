package returnfirstnonull;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.ObjectUtils;

import org.junit.Before;
import org.junit.Test;

import com.google.common.base.MoreObjects;
import com.google.common.base.Predicates;
import com.google.common.collect.Iterables;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ReturnFirstNonNullUnitTest {

    private List<Object> objects;

    @Before
    public void init() {
        objects = Arrays.asList(
            null,
            new Object(),
            new Object()
        );
    }

    @Test
    public void givenListOfObjects_whenIteratingWithForLoop_thenReturnFirstNonNull() {
        Object object = null; 
        for(int i = 0; i < objects.size(); i++) {
            if(objects.get(i) != null) {
                object = objects.get(i);
                break;
            }
        }

        assertTrue(object != null);
        assertEquals(objects.get(1), object);
    }

    @Test
    public void givenListOfObjects_whenIteratingWithEnhancedForLoop_thenReturnFirstNonNull() {
        Object object = null; 
        for(Object o: objects) {
            if(o != null) {
                object = o;
                break;
            }
        }

        assertTrue(object != null);
        assertEquals(objects.get(1), object);
    }

    @Test
    public void givenListOfObjects_whenFilterIsLambdaNullCheck_thenReturnFirstNonNull() {
        Optional<Object> object = objects
            .stream()
            .filter(o -> o != null)
            .findFirst();

        assertTrue(object.isPresent());
        assertEquals(objects.get(1), object.get());
    }

    @Test
    public void givenListOfObjects_whenFilterIsMethodRefNullCheck_thenReturnFirstNonNull() {
        Optional<Object> object = objects
            .stream()
            .filter(Objects::nonNull)
            .findFirst();

        assertTrue(object.isPresent());
        assertEquals(objects.get(1), object.get());
    }

    @Test
    public void givenListOfObjects_whenUsingApacheCommonsLang3_thenReturnFirstNonNull() {
        Object object = ObjectUtils.firstNonNull(objects.toArray());

        assertTrue(object != null);
        assertEquals(objects.get(1), object);
    }

    @Test
    public void givenTwoObjects_whenUsingGoogleGuava_thenReturnFirstNonNull() {
        Object object1 = null;
        Object object2 = new Object();
        Object object = MoreObjects.firstNonNull(object1, object2);

        assertTrue(object != null);
        assertEquals(object2, object);
    }

    @Test
    public void givenListOfObjects_whenUsingGoogleGuava_thenReturnFirstNonNull() {
        Object object = Iterables.find(objects, Predicates.notNull());

        assertTrue(object != null);
        assertEquals(objects.get(1), object);
    }
}