package baeldung.com;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

public class RegistrationUnitTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationUnitTest.class);

    @Test
    public void whenCalledFromSuite_thanOK() {
        LOGGER.info("Registration successful");
    }
}
