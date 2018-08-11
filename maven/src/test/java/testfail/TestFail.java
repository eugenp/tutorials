package testfail;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class TestFail {
    @Test
    public void whenMessageAssigned_thenItIsNotNull() {
        String message = "hello there";
        message = null;
        assertNotNull(message);
    }
    
}
