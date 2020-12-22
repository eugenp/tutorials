package com.baeldung.camel.jackson;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.ListJacksonDataFormat;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

public class FruitArrayJacksonUnmarshalUnitTest extends CamelTestSupport {

    @Test
    public void givenJsonFruitArray_whenUnmarshalled_thenSuccess() throws Exception {
        MockEndpoint mock = getMockEndpoint("mock:marshalledObject");
        mock.expectedMessageCount(1);
        mock.message(0).body().isInstanceOf(List.class);

        String json = readJsonFromFile("/json/fruit-array.json");
        template.sendBody("direct:jsonInput", json);
        assertMockEndpointsSatisfied();

        @SuppressWarnings("unchecked")
        List<Fruit> fruitList = mock.getReceivedExchanges().get(0).getIn().getBody(List.class);
        assertNotNull("Fruit lists should not be null", fruitList);

        assertEquals("There should be two fruits", 2, fruitList.size());

        Fruit fruit = fruitList.get(0);
        assertEquals("Fruit name", "Banana", fruit.getName());
        assertEquals("Fruit id", 100, fruit.getId());

        fruit = fruitList.get(1);
        assertEquals("Fruit name", "Apple", fruit.getName());
        assertEquals("Fruit id", 101, fruit.getId());
    }
    
    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {

                from("direct:jsonInput").unmarshal(new ListJacksonDataFormat(Fruit.class))
                    .to("mock:marshalledObject");
            }
        };
    }

    private String readJsonFromFile(String path) throws URISyntaxException, IOException {
        URL resource = FruitArrayJacksonUnmarshalUnitTest.class.getResource(path);
        return new String(Files.readAllBytes(Paths.get(resource.toURI())));
    }

}
