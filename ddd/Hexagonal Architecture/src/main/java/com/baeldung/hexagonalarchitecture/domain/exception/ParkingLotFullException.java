package com.baeldung.hexagonalarchitecture.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INSUFFICIENT_STORAGE, reason = "Parking lot full")
public class ParkingLotFullException extends RuntimeException{

}
