package info.customer;

import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;

@QuarkusTestResource(CustomerServiceTestcontainersManager.class)
@QuarkusTest
@TestHTTPEndpoint(CustomerResource.class)
class CustomerResourceLiveTest {

    @ParameterizedTest
    @MethodSource(value = "customerDataProvider")
    void findById(long customerId, String customerName, int orderSize) {
        RestAssured.given()
            .pathParam("id", customerId)
            .get()
            .then()
            .statusCode(200);

        Customer response = RestAssured.given()
            .pathParam("id", customerId)
            .get()
            .thenReturn()
            .as(Customer.class);

        Assertions.assertEquals(customerId, response.id);
        Assertions.assertEquals(customerName, response.name);
        Assertions.assertEquals(orderSize, response.orders.size());

        response.orders.forEach(order -> {
            Assertions.assertNotNull(order.getId());
            Assertions.assertNotNull(order.getDescription());
        });
    }

    private static Stream<Arguments> customerDataProvider() {
        return Stream.of(Arguments.of(1, "Customer 1", 3), Arguments.of(2, "Customer 2", 1), Arguments.of(3, "Customer 3", 0));
    }

}
