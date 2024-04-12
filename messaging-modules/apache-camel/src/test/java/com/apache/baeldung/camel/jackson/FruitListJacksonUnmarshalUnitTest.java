package com.apache.baeldung.camel.jackson;

import com.baeldung.camel.apache.Application;
import com.baeldung.camel.apache.jackson.Fruit;
import com.baeldung.camel.apache.jackson.FruitList;
import org.apache.camel.Configuration;
import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;

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
@SpringBootTest(classes = {Application.class, FruitListJacksonUnmarshalUnitTest.TestConfig.class})
public class FruitListJacksonUnmarshalUnitTest {

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
                    from("direct:jsonInput").unmarshal(new JacksonDataFormat(FruitList.class))
                      .to("mock:marshalledObject");
                }
            };
        }
    }

    @Test
    public void givenJsonFruitList_whenUnmarshalled_thenSuccess() throws Exception {
        mock.setExpectedMessageCount(1);
        mock.message(0).body().isInstanceOf(FruitList.class);

        String json = readJsonFromFile("/json/fruit-list.json");
        template.sendBody("direct:jsonInput", json);
        mock.assertIsSatisfied();

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

    private String readJsonFromFile(String path) throws URISyntaxException, IOException {
        URL resource = FruitListJacksonUnmarshalUnitTest.class.getResource(path);
        return new String(Files.readAllBytes(Paths.get(resource.toURI())), StandardCharsets.UTF_8);
    }

}
