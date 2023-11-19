package com.apache.baeldung.camel.jackson;

import com.baeldung.camel.apache.Application;
import com.baeldung.camel.apache.jackson.Fruit;
import org.apache.camel.Configuration;
import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.ListJacksonDataFormat;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.annotation.DirtiesContext;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertNotNull;

@CamelSpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest(classes = {Application.class, FruitArrayJacksonUnmarshalUnitTest.TestConfig.class})
public class FruitArrayJacksonUnmarshalUnitTest {

    @Autowired
    private ProducerTemplate template;

    @EndpointInject("mock:marshalledObject")
    private MockEndpoint mock;

    @Configuration
    static class TestConfig {
        @Bean
        RoutesBuilder route() {
            return new RouteBuilder() {
                @Override
                public void configure() throws Exception {
                    from("direct:jsonInput").unmarshal(new ListJacksonDataFormat(Fruit.class))
                      .to("mock:marshalledObject");
                }
            };
        }
    }

    @Test
    public void givenJsonFruitArray_whenUnmarshalled_thenSuccess() throws Exception {
        mock.setExpectedMessageCount(1);
        mock.message(0).body().isInstanceOf(List.class);

        String json = readJsonFromFile("/json/fruit-array.json");
        template.sendBody("direct:jsonInput", json);
        mock.assertIsSatisfied();

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

    private String readJsonFromFile(String path) throws URISyntaxException, IOException {
        URL resource = FruitArrayJacksonUnmarshalUnitTest.class.getResource(path);
        return new String(Files.readAllBytes(Paths.get(resource.toURI())), StandardCharsets.UTF_8);
    }

}
