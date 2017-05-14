
import static com.lightbend.lagom.javadsl.testkit.ServiceTest.*;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import sample.cargotracker.registration.api.RegistrationService;

/**
 * Created by myfear on 03/04/16.
 */
public class RegistrationServiceTest {

    @Test
    public void shouldSayHello() throws Exception {
        withServer(defaultSetup(), server -> {
            RegistrationService service = server.client(RegistrationService.class);

          //TODO add meaningfull test
        });
    }

}
