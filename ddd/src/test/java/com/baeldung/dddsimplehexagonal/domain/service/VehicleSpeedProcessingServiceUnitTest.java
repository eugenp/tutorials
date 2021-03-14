package com.baeldung.dddsimplehexagonal.domain.service;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.baeldung.dddsimplehexagonal.domain.port.incoming.IncomingSpeedDataDTO;
import com.baeldung.dddsimplehexagonal.domain.port.outgoing.OutgoingSpeedDataDTO;
import com.baeldung.dddsimplehexagonal.domain.port.outgoing.SpeedingOffenceOutgoingPort;

class VehicleSpeedProcessingServiceUnitTest {
    
    private VehicleSpeedProcessingService testedService;
    private SpeedingOffenceOutgoingPort mockPort;

    @BeforeEach
    void setUp() throws Exception {
        mockPort = mock(SpeedingOffenceOutgoingPort.class);
        testedService = new VehicleSpeedProcessingService();
        testedService.setOutgoingPortAdapter(mockPort);
    }

    @Test
    void givenSpeed62AboveLimit50_whenAddSpeedDataWithService_thenSpeedingOffenseIsSent() {
        
        IncomingSpeedDataDTO incomingDTO = new IncomingSpeedDataDTO("V96JCL", 62.0F, 50.0F);
        
        testedService.addSpeedData(incomingDTO);
        
        OutgoingSpeedDataDTO outgoingDTO = new OutgoingSpeedDataDTO("V96JCL", 62.0F, 50.0F);
        verify(mockPort).addSpeedingOffenseData(outgoingDTO);
    }

    @Test
    void givenSpeed62BelowLimit70_whenAddSpeedDataWithService_thenSpeedingOffenseNotSent() {
        
        IncomingSpeedDataDTO incomingDTO = new IncomingSpeedDataDTO("V96JCL", 62.0F, 70.0F);
        
        testedService.addSpeedData(incomingDTO);
        
        verify(mockPort, never()).addSpeedingOffenseData(any(OutgoingSpeedDataDTO.class));
    }
}
