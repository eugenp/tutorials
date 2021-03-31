package com.baeldung.dddsimplehexagonal.adapter.driven;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.baeldung.dddsimplehexagonal.domain.VehicleSpeedData;
import com.baeldung.dddsimplehexagonal.external.driven.OnlineAutoFineIssuingInterface;
import com.fasterxml.jackson.databind.ObjectMapper;

class OnlineAutoFineIssuingAdapterUnitTest {
    
    private OnlineAutoFineIssuingInterface fineIssuingSystem;
    private OnlineAutoFineIssuingAdapter testedAdapter;

    @BeforeEach
    void setUp() throws Exception {
        fineIssuingSystem = mock(OnlineAutoFineIssuingInterface.class);
        
        testedAdapter = new OnlineAutoFineIssuingAdapter();
        testedAdapter.setOnlineFineIssuingSystem(fineIssuingSystem);
    }
    
    @Test
    void givenSpeedData_whenAddSpeedingOffenseData_thenSendToSpeedingFineSystemSuccess() 
      throws Exception {
        VehicleSpeedData speedData = new VehicleSpeedData("FG5G43", 55, 60);
        
        testedAdapter.addSpeedingOffenseData(speedData);
        
        String jsonStr = new ObjectMapper().writeValueAsString(speedData);
        verify(fineIssuingSystem).sendAutoFineIssuingJsonStr(jsonStr);
    }
}
