package com.baeldung.hexagonalarchitecture.port.secondary.adapter;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

import com.baeldung.hexagonalarchitecture.domain.ParkingLot;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ParkingLotMongoRepositoryTest {

    @InjectMocks
    private ParkingLotMongoRepository parkingLotMongoRepository;

    @Mock
    private ParkingLotRepository parkingLotRepository;

    @Test
    void givenParkingLotPresent_whenQueried_thenExpectParkingLot() {
        ObjectId parkingLotId = new ObjectId();
        ParkingLot parkingLot = ParkingLot.builder().id(parkingLotId).capacity(5).build();

        when(parkingLotMongoRepository.findById(parkingLotId)).thenReturn(Optional.of(parkingLot));

        assertThat(parkingLotMongoRepository.findById(parkingLotId))
            .isEqualTo(Optional.of(parkingLot));
    }

    @Test
    void givenParkingLot_thenSaveAndReturnSavedParkingLot() {
        ParkingLot parkingLot = ParkingLot.builder().capacity(5).build();

        when(parkingLotRepository.save(parkingLot)).thenReturn(parkingLot);

        assertThat(parkingLotMongoRepository.save(parkingLot)).isEqualTo(parkingLot);
    }
}