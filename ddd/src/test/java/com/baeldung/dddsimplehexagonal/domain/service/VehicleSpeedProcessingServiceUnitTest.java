package com.baeldung.dddsimplehexagonal.domain.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.baeldung.dddsimplehexagonal.domain.VehicleSpeedData;
import com.baeldung.dddsimplehexagonal.domain.port.outgoing.SpeedingOffenceOutgoingPort;

class VehicleSpeedProcessingServiceUnitTest {
    
    private VehicleSpeedProcessingService testedService;
    private SpeedingOffenceOutgoingPort mockOutgoingPort;

    @BeforeEach
    void setUp() throws Exception {
        mockOutgoingPort = mock(SpeedingOffenceOutgoingPort.class);
        testedService = new VehicleSpeedProcessingService();
        testedService.setOutgoingPortAdapter(mockOutgoingPort);
    }

    @Test
    void givenSpeed62AboveLimit50_whenAddSpeedDataWithService_thenSpeedingOffenseIsSent() throws Exception {
        
        VehicleSpeedData speedData = new VehicleSpeedData("V96JCL", 62.0F, 50.0F);
        
        testedService.addSpeedData(speedData);
        
        verify(mockOutgoingPort).addSpeedingOffenseData(speedData);
    }

    @Test
    void givenSpeed62BelowLimit70_whenAddSpeedDataWithService_thenSpeedingOffenseNotSent() throws Exception {
        
        VehicleSpeedData speedData = new VehicleSpeedData("V96JCL", 62.0F, 70.0F);
        
        testedService.addSpeedData(speedData);
        
        verify(mockOutgoingPort, never()).addSpeedingOffenseData(any(VehicleSpeedData.class));
    }
}
