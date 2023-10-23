package returnfirstnonempty;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ReturnFirstNonEmptyOptionalUnitTest {

    private List<Optional<Object>> optionals;

    @Before
    public void init() {
        optionals = Arrays.asList(
            Optional.empty(),
            Optional.of(new Object()),
            Optional.empty()
        );
    }

    @Test
    public void givenListOfOptionals_thenReturnFirstNonEmpty() {
        Object object = optionals
            .stream()
            .filter(Optional::isPresent)
            .map(Optional::get)
            .findFirst();

        assertTrue(object != null);
        assertEquals(optionals.get(1), object);
    }
}