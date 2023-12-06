package src.test.java.com.baeldung.rest;


import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@QuarkusTest
public class VirtualThreadAppTest {

    @Test
    public void whenProcessEndpointThenReturnMockedGreeting() {
        given().when().get("/greetings").then().statusCode(200).body(is("MOCKED GREETING"));
    }
}