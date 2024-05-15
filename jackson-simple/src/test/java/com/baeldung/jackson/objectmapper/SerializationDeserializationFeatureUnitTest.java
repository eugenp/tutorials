package com.baeldung.jackson.objectmapper;

import com.baeldung.jackson.objectmapper.dto.Car;
import com.baeldung.jackson.objectmapper.dto.Request;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class SerializationDeserializationFeatureUnitTest {

    final String EXAMPLE_JSON = "{ \"color\" : \"Black\", \"type\" : \"BMW\" }";
    final String JSON_CAR = "{ \"color\" : \"Black\", \"type\" : \"Fiat\", \"year\" : \"1970\" }";
    final String JSON_ARRAY = "[{ \"color\" : \"Black\", \"type\" : \"BMW\" }, { \"color\" : \"Red\", \"type\" : \"BMW\" }]";

    @Test
    public void whenFailOnUnkownPropertiesFalse_thanJsonReadCorrectly() throws Exception {

        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        final Car car = objectMapper.readValue(JSON_CAR, Car.class);
        final JsonNode jsonNodeRoot = objectMapper.readTree(JSON_CAR);
        final JsonNode jsonNodeYear = jsonNodeRoot.get("year");
        final String year = jsonNodeYear.asText();

        assertNotNull(car);
        assertThat(car.getColor(), equalTo("Black"));
        assertThat(year, containsString("1970"));
    }

    @Test
    public void whenCustomSerializerDeserializer_thanReadWriteCorrect() throws Exception {
        final ObjectMapper mapper = new ObjectMapper();
        final SimpleModule serializerModule = new SimpleModule("CustomSerializer", new Version(1, 0, 0, null, null, null));
        serializerModule.addSerializer(Car.class, new CustomCarSerializer());
        mapper.registerModule(serializerModule);
        final Car car = new Car("yellow", "renault");
        final String carJson = mapper.writeValueAsString(car);
        assertThat(carJson, containsString("renault"));
        assertThat(carJson, containsString("model"));

        final SimpleModule deserializerModule = new SimpleModule("CustomCarDeserializer", new Version(1, 0, 0, null, null, null));
        deserializerModule.addDeserializer(Car.class, new CustomCarDeserializer());
        mapper.registerModule(deserializerModule);
        final Car carResult = mapper.readValue(EXAMPLE_JSON, Car.class);
        assertNotNull(carResult);
        assertThat(carResult.getColor(), equalTo("Black"));
    }

    @Test
    public void whenDateFormatSet_thanSerializedAsExpected() throws Exception {
        final ObjectMapper objectMapper = new ObjectMapper();
        final Car car = new Car("yellow", "renault");
        final Request request = new Request();
        request.setCar(car);
        request.setDatePurchased(new Date());
        final DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm a z");
        objectMapper.setDateFormat(df);
        final String carAsString = objectMapper.writeValueAsString(request);
        assertNotNull(carAsString);
        assertThat(carAsString, containsString("datePurchased"));
    }

    @Test
    public void whenUseJavaArrayForJsonArrayTrue_thanJsonReadAsArray() throws Exception {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true);
        final Car[] cars = objectMapper.readValue(JSON_ARRAY, Car[].class);
        for (final Car car : cars) {
            assertNotNull(car);
            assertThat(car.getType(), equalTo("BMW"));
        }
    }
}
