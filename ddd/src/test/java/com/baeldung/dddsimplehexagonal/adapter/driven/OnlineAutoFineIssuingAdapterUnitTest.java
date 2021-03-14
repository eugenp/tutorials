package com.baeldung.dddsimplehexagonal.adapter.driven;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import com.baeldung.dddsimplehexagonal.adapter.driven.exception.DrivenAdapterRuntimeException;
import com.baeldung.dddsimplehexagonal.domain.port.outgoing.OutgoingSpeedDataDTO;
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
    void givenValidSpeedDataDTO_whenAddSpeedingOffenseData_thenSendToFineSystemSuccess() 
      throws Exception {
        OutgoingSpeedDataDTO outgoingDTO = new OutgoingSpeedDataDTO("FG5G43", 55, 60);
        
        testedAdapter.addSpeedingOffenseData(outgoingDTO);
        
        ObjectMapper mapper = new ObjectMapper();
        String fineJsonStr = mapper.writeValueAsString(outgoingDTO);
        verify(fineIssuingSystem).sendAutoFineIssuingJsonStr(fineJsonStr);
    }

    @Test
    void givenNullRegPlateNoInDTO_whenAddSpeedingOffenseData_thenRuntimeExceptionThrown() {
        OutgoingSpeedDataDTO outgoingDTO = new OutgoingSpeedDataDTO(null, 55, 60);
        
        Executable executable = () -> testedAdapter.addSpeedingOffenseData(outgoingDTO);
        
        assertThrows(DrivenAdapterRuntimeException.class, executable);
    }
    
    @Test
    void givenNegativeSpeedInDTO_whenAddSpeedingOffenseData_thenRuntimeExceptionThrown() {
        OutgoingSpeedDataDTO outgoingDTO = new OutgoingSpeedDataDTO("FG5G43", -55, 60);
        
        Executable executable = () -> testedAdapter.addSpeedingOffenseData(outgoingDTO);
        
        assertThrows(DrivenAdapterRuntimeException.class, executable);
    }

    @Test
    void givenNegativeSpeedLimitInDTO_whenAddSpeedingOffenseData_thenRuntimeExceptionThrown() {
        OutgoingSpeedDataDTO outgoingDTO = new OutgoingSpeedDataDTO("FG5G43", 55, -60);
        
        Executable executable = () -> testedAdapter.addSpeedingOffenseData(outgoingDTO);
        
        assertThrows(DrivenAdapterRuntimeException.class, executable);
    }
}
