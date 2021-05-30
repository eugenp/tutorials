package com.baeldung.hexagonalarchitecture.port.primary;

import com.baeldung.hexagonalarchitecture.domain.Car;
import com.baeldung.hexagonalarchitecture.domain.ParkingLot;
import com.baeldung.hexagonalarchitecture.domain.usecase.ParkingLotUseCase;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/parking-lot")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ParkingLotController {

    private final ParkingLotUseCase parkingLotUseCase;

    @PostMapping("/{parkingLotId}/park")
    public ParkingLot park(@PathVariable ObjectId parkingLotId, @RequestBody Car car) {
        return parkingLotUseCase.park(parkingLotId, car);
    }

    @DeleteMapping("/{parkingLotId}/un-park")
    public ParkingLot unPark(@PathVariable ObjectId parkingLotId, @RequestBody Car car) {
        return parkingLotUseCase.unPark(parkingLotId, car);
    }

    @PostMapping()
    public ParkingLot create(@RequestParam int capacity) {
        return parkingLotUseCase.create(capacity);
    }

    @GetMapping("/{parkingLotId}")
    public ParkingLot get(@PathVariable ObjectId parkingLotId) {
        return parkingLotUseCase.get(parkingLotId);
    }

}
