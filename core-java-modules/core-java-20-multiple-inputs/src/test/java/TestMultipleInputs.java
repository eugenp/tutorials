import com.baeldung.multipleinputs.MultipleInputs;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.InputMismatchException;
public class TestMultipleInputs {
    @Test
    public void testUsingSpaceDelimiter() {
        String input = "10 20\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        MultipleInputs mi = new MultipleInputs();
        mi.UsingSpaceDelimiter();
        // You can add assertions here to verify the behavior of the method
    }

    @Test
    public void testUsingREDelimiter() {
        String input = "30, 40\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        MultipleInputs mi = new MultipleInputs();
        mi.UsingREDelimiter();
        // You can add assertions here to verify the behavior of the method
    }

    @Test
    public void testUsingCustomDelimiter() {
        String input = "50; 60\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        MultipleInputs mi = new MultipleInputs();
        mi.UsingCustomDelimiter();
        // You can add assertions here to verify the behavior of the method
    }

    @Test
    public void testInvalidInput() {
        String input = "abc\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        MultipleInputs mi = new MultipleInputs();
        Assertions.assertThrows(InputMismatchException.class, mi::UsingSpaceDelimiter);
    }
}

