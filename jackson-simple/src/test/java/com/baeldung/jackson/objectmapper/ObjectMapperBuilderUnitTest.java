package com.baeldung.jackson.objectmapper;

import java.util.Date;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import com.baeldung.jackson.objectmapper.dto.Car;
import com.baeldung.jackson.objectmapper.dto.Request;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

public class ObjectMapperBuilderUnitTest {

    ObjectMapper mapper = new ObjectMapperBuilder()
      .enableIndentation()
      .dateFormat()
      .preserveOrder(true)
      .build();

    Car givenCar = new Car("White", "Sedan");
    String givenCarJsonStr = "{ \"color\" : \"White\", \"type\" : \"Sedan\" }";

    @Test
    public void whenReadCarJsonStr_thenReturnCarObjectCorrectly() throws JacksonException {
        Car actual = mapper.readValue(givenCarJsonStr, Car.class);
        Assertions.assertEquals("White", actual.getColor());
        Assertions.assertEquals("Sedan", actual.getType());
    }

    @Test
    public void whenWriteRequestObject_thenReturnRequestJsonStrCorrectly() throws JacksonException {
        Request request = new Request();
        request.setCar(givenCar);
        Date date = new Date(1684909857000L);
        request.setDatePurchased(date);

        String actual = mapper.writeValueAsString(request);
        String expected = "{\n" + "  \"car\" : {\n" + "    \"color\" : \"White\",\n" +
          "    \"type\" : \"Sedan\"\n" + "  },\n" + "  \"datePurchased\" : \"2023-05-24 12:00 PM IST\"\n" +
          "}";
        Assertions.assertEquals(expected, actual);
    }
}
