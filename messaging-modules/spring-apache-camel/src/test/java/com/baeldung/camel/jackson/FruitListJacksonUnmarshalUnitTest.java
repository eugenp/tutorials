package com.baeldung.camel.jackson;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

public class FruitListJacksonUnmarshalUnitTest extends CamelTestSupport {

    @Test
    public void givenJsonFruitList_whenUnmarshalled_thenSuccess() throws Exception {
        MockEndpoint mock = getMockEndpoint("mock:marshalledObject");
        mock.expectedMessageCount(1);
        mock.message(0).body().isInstanceOf(FruitList.class);

        String json = readJsonFromFile("/json/fruit-list.json");
        template.sendBody("direct:jsonInput", json);
        assertMockEndpointsSatisfied();

        FruitList fruitList = mock.getReceivedExchanges().get(0).getIn().getBody(FruitList.class);
        assertNotNull("Fruit lists should not be null", fruitList);

        List<Fruit> fruits = fruitList.getFruits();
        assertEquals("There should be two fruits", 2, fruits.size());

        Fruit fruit = fruits.get(0);
        assertEquals("Fruit name", "Banana", fruit.getName());
        assertEquals("Fruit id", 100, fruit.getId());

        fruit = fruits.get(1);
        assertEquals("Fruit name", "Apple", fruit.getName());
        assertEquals("Fruit id", 101, fruit.getId());
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("direct:jsonInput").unmarshal(new JacksonDataFormat(FruitList.class))
                    .to("mock:marshalledObject");
            }
        };
    }

    private String readJsonFromFile(String path) throws URISyntaxException, IOException {
        URL resource = FruitListJacksonUnmarshalUnitTest.class.getResource(path);
        return new String(Files.readAllBytes(Paths.get(resource.toURI())));
    }

}
