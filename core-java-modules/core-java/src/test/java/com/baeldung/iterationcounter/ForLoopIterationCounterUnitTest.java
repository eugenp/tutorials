import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.baeldung.iterationcounter.ForLoopIterationCounter;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ForLoopIterationCounterUnitTest {
    private static final List<String> COLORS = Arrays.asList("red", "blue", "yellow", "green");


    @Mock
    private ForLoopIterationCounter iterationCounter = new ForLoopIterationCounter();;

    @Test
    public void given_listOfStringsAndCounter_when_forEachCounter_then_returnNumberOfIterations() {
        ForLoopIterationCounter forLoopIterationCounter = new ForLoopIterationCounter();
        int counter = forLoopIterationCounter.forEachCounter(COLORS);

        assertEquals(counter, COLORS.size());
    }

    @Test
    public void given_listOfStringsAndAtomicCounter_when_forEachLambdaAtomicCounter_then_returnNumberOfIterations() {
        ForLoopIterationCounter forLoopIterationCounter = new ForLoopIterationCounter();
        int counter = forLoopIterationCounter.forEachLambdaAtomicCounter(COLORS);

        assertEquals(counter, COLORS.size());
    }

    @Test
    public void given_listOfStrings_when_forEachLambda_then_returnNumberOfIterations() {
        iterationCounter.forEachLambda(COLORS);

        verify(iterationCounter, times(1)).forEachLambda(COLORS);
    }
}
