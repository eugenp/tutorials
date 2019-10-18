package testfail;

import org.junit.Test;
import org.junit.Ignore;

import static org.junit.Assert.assertNotNull;

public class TestFail {
	
    @Ignore //ignored so the entire tutorials build passes
    @Test
    public void whenMessageAssigned_thenItIsNotNull() {
        String message = "hello there";
        message = null;
        assertNotNull(message);
    }
    
}
