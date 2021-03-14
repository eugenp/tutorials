package com.baeldung.dddsimplehexagonal.adapter.driven;

import static com.baeldung.dddsimplehexagonal.external.driven.PoliceSpeedingOffenseNotifInterface.ITEM_DELIMITER;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import com.baeldung.dddsimplehexagonal.adapter.driven.exception.DrivenAdapterRuntimeException;
import com.baeldung.dddsimplehexagonal.domain.port.outgoing.OutgoingSpeedDataDTO;
import com.baeldung.dddsimplehexagonal.external.driven.PoliceSpeedingOffenseNotifInterface;

class PoliceSpeedingOffenseNotifAdapterUnitTest {
    
    private PoliceSpeedingOffenseNotifAdapter testedAdapter;
    PoliceSpeedingOffenseNotifInterface speedingOffenseNotifSystem;

    @BeforeEach
    void setUp() throws Exception {
        speedingOffenseNotifSystem = mock(PoliceSpeedingOffenseNotifInterface.class);
        testedAdapter = new PoliceSpeedingOffenseNotifAdapter();
        testedAdapter.setSpeedingOffenseNotifSystem(speedingOffenseNotifSystem);
    }
    
    @Test
    void givenValidSpeedDataDTO_whenAddSpeedingOffenseData_thenSendToNotifSystemSuccess() {
        OutgoingSpeedDataDTO outgoingDTO = new OutgoingSpeedDataDTO("FG5G43", 55, 60);
        
        testedAdapter.addSpeedingOffenseData(outgoingDTO);
        
        String offenseNotifStr = "FG5G43" + ITEM_DELIMITER + "55.0" + ITEM_DELIMITER + "60.0";
        verify(speedingOffenseNotifSystem).sendSpeedingOffenseNotification(offenseNotifStr);
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
