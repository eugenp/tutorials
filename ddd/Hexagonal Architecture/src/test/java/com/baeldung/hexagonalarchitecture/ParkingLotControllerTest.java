package com.baeldung.hexagonalarchitecture;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.baeldung.hexagonalarchitecture.domain.Car;
import com.baeldung.hexagonalarchitecture.domain.ParkingLot;
import com.baeldung.hexagonalarchitecture.domain.usecase.ParkingLotUseCase;
import com.baeldung.hexagonalarchitecture.port.primary.ParkingLotController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ParkingLotController.class)
class ParkingLotControllerTest {

    @MockBean
    private ParkingLotUseCase parkingLotUseCase;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    private static final Car CAR = new Car("123", "Tesla", "Black");

    @Test
    void whenParkAPICalled_thenReturnParkingLotFromUseCase() throws Exception {
        ObjectId parkingLotId = new ObjectId();
        ParkingLot parkingLot = ParkingLot.builder().id(parkingLotId).build();
        when(parkingLotUseCase.park(parkingLotId, CAR)).thenReturn(parkingLot);

        mockMvc.perform(post("/parking-lot/{parkingLotId}/park", parkingLotId.toString())
            .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(CAR)))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(parkingLot)));
    }

    @Test
    void whenUnParkAPICalled_thenReturnParkingLotFromUseCase() throws Exception {
        ObjectId parkingLotId = new ObjectId();
        ParkingLot parkingLot = ParkingLot.builder().id(parkingLotId).build();
        when(parkingLotUseCase.unPark(parkingLotId, CAR)).thenReturn(parkingLot);

        mockMvc.perform(delete("/parking-lot/{parkingLotId}/un-park", parkingLotId.toString())
            .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(CAR)))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(parkingLot)));
    }

    @Test
    void whenCreateAPICalled_thenReturnParkingLotFromUseCase() throws Exception {
        int capacity = 10;
        ParkingLot parkingLot = ParkingLot.builder().capacity(capacity).build();
        when(parkingLotUseCase.create(capacity)).thenReturn(parkingLot);

        mockMvc.perform(post("/parking-lot/")
            .param("capacity", String.valueOf(capacity))
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(parkingLot)));
    }


    @Test
    void whenGetAPICalled_thenReturnParkingLotFromUseCase() throws Exception {
        ObjectId parkingLotId = new ObjectId();
        ParkingLot parkingLot = ParkingLot.builder().id(parkingLotId).build();

        when(parkingLotUseCase.get(parkingLotId)).thenReturn(parkingLot);

        mockMvc.perform(get("/parking-lot/{parkingLotId}", parkingLotId.toString())
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(parkingLot)));
    }
}