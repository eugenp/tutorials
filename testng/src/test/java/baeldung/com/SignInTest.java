package baeldung.com;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

public class SignInTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(SignInTest.class);

    @Test
    public void whenCalledFromSuite_thanOK() {
        LOGGER.info("SignIn successful");
    }
}
