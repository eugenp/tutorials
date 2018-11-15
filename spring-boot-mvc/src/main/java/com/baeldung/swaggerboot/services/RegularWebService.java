package com.baeldung.swaggerboot.services;

import com.baeldung.swaggerboot.transfer.CustomResponse;
import org.springframework.stereotype.Service;

import static com.baeldung.swaggerboot.Constants.DEFAULT_ERROR;
import static com.baeldung.swaggerboot.Constants.DEFAULT_GREETING;

@Service
public class RegularWebService {

  public CustomResponse example() {
    try {
      return new CustomResponse(0, DEFAULT_GREETING);
    } catch (Exception ex) {
      return new CustomResponse(0, DEFAULT_ERROR);
    }
  }

}