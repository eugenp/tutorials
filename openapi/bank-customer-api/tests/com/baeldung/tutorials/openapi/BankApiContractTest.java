import au.com.dius.pact.consumer.junit.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit.loader.PactFolder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@ExtendWith(PactConsumerTestExt.class)
@PactFolder("pacts")
public class BankApiContractTest {

    @Pact(consumer = "BankCustomerApp", provider = "BankAPI")
    public RequestResponsePact createPact(PactDslWithProvider builder) {
        return builder
            .given("a customer exists")
            .uponReceiving("a request for a customer")
            .path("/customers/1")
            .method("GET")
            .willRespondWith()
            .status(200)
            .body("{\"id\":1,\"firstName\":\"Jane\",\"lastName\":\"Doe\",\"accountNumber\":\"987654321\",\"balance\":1500.00}")
            .toPact();
    }

    @Test
    public void shouldReturnValidBankCustomer() {
        given()
            .port(8080)
            .when()
            .get("/customers/1")
            .then()
            .statusCode(200)
            .body("firstName", equalTo("Jane"))
            .body("lastName", equalTo("Doe"));
    }
}
