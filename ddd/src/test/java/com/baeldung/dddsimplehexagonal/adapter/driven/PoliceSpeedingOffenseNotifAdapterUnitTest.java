package com.baeldung.dddsimplehexagonal.adapter.driven;

import static com.baeldung.dddsimplehexagonal.external.driven.PoliceSpeedingOffenseNotifInterface.ITEM_DELIMITER;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.baeldung.dddsimplehexagonal.domain.VehicleSpeedData;
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
    void givenSpeedData_whenAddSpeedingOffenseData_thenSendToNotifSystemSuccess() {
        VehicleSpeedData speedData = new VehicleSpeedData("FG5G43", 55, 60);
        
        testedAdapter.addSpeedingOffenseData(speedData);
        
        String offenseNotifStr = "FG5G43" + ITEM_DELIMITER + "55.0" + ITEM_DELIMITER + "60.0";
        verify(speedingOffenseNotifSystem).sendSpeedingOffenseNotification(offenseNotifStr);
    }    
}
