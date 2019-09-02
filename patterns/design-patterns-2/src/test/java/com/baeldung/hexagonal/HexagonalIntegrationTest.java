package com.baeldung.hexagonal;

import com.baeldung.hexagonal.adapters.InMemoryStorageService;
import com.baeldung.hexagonal.adapters.JsonEncoderService;
import com.baeldung.hexagonal.adapters.NoOpEncoderService;
import com.baeldung.hexagonal.core.DataReceiverServiceImpl;
import com.baeldung.hexagonal.ports.DataReceiverService;
import com.baeldung.hexagonal.ports.EncoderService;
import com.baeldung.hexagonal.ports.StorageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HexagonalIntegrationTest {
    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void whenJsonEncoderAndInMemoryStorage_thenEncodedDataStoredSuccesfully() throws Exception {
        int id = 123;
        Car inputCar = new Car();
        inputCar.color = "white";
        inputCar.type = "sedan";
        EncoderService encoderService = new JsonEncoderService();
        StorageService storageService = new InMemoryStorageService();
        DataReceiverService dataReceiverService = new DataReceiverServiceImpl(encoderService, storageService);
        dataReceiverService.process(id, inputCar);

        String actual = storageService.get(id);
        Car actualCar = objectMapper.readValue(actual, Car.class);
        assertEquals(inputCar.color, actualCar.color);
        assertEquals(inputCar.type, actualCar.type);
    }

    @Test
    public void whenNoOpEncoderAndInMemotyStorage_thenEncodedDataStoredSuccesfully() throws Exception {
        int id = 123;
        String inputStr = "baeldung.com";
        EncoderService encoderService = new NoOpEncoderService();
        StorageService storageService = new InMemoryStorageService();
        DataReceiverService dataReceiverService = new DataReceiverServiceImpl(encoderService, storageService);
        dataReceiverService.process(id, inputStr);

        String actualStr = storageService.get(id);
        assertEquals(inputStr, actualStr);
    }

}
