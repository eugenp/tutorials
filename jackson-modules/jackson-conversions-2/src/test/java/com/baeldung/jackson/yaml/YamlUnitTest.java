package com.baeldung.jackson.yaml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator.Feature;

public class YamlUnitTest {
    private ObjectMapper mapper;

    @Before
    public void setup() {
        mapper = new ObjectMapper(new YAMLFactory().disable(Feature.WRITE_DOC_START_MARKER));
        mapper.findAndRegisterModules();
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Test
    public void givenYamlInput_ObjectCreated() throws JsonParseException, JsonMappingException, IOException {
        Order order = mapper.readValue(new File("src/test/resources/yaml/orderInput.yaml"), Order.class);
        assertEquals("A001", order.getOrderNo());
        assertEquals(LocalDate.parse("2019-04-17", DateTimeFormatter.ISO_DATE), order.getDate());
        assertEquals("Customer, Joe", order.getCustomerName());
        assertEquals(2, order.getOrderLines()
            .size());
    }

    @Test
    public void givenYamlObject_FileWritten() throws JsonGenerationException, JsonMappingException, IOException {
        List<OrderLine> lines = new ArrayList<>();
        lines.add(new OrderLine("Copper Wire (200ft)", 1, new BigDecimal(50.67).setScale(2, RoundingMode.HALF_UP)));
        lines.add(new OrderLine("Washers (1/4\")", 24, new BigDecimal(.15).setScale(2, RoundingMode.HALF_UP)));
        Order order = new Order(
            "B-9910", 
            LocalDate.parse("2019-04-18", DateTimeFormatter.ISO_DATE), 
            "Customer, Jane", 
            lines);
        mapper.writeValue(new File("src/test/resources/yaml/orderOutput.yaml"), order);

        File outputYaml = new File("src/test/resources/yaml/orderOutput.yaml");
        assertTrue(outputYaml.exists());
    }
}
