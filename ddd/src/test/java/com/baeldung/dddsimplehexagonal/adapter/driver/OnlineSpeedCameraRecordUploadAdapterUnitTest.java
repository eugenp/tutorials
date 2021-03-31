package com.baeldung.dddsimplehexagonal.adapter.driver;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import com.baeldung.dddsimplehexagonal.domain.VehicleSpeedData;
import com.baeldung.dddsimplehexagonal.domain.port.incoming.SpeedDataIncomingPort;

class OnlineSpeedCameraRecordUploadAdapterUnitTest {
    
    private OnlineSpeedCameraRecordUploadAdapter testedAdapter;
    private SpeedDataIncomingPort port;

    @BeforeEach
    void setUp() throws Exception {
        port = mock(SpeedDataIncomingPort.class);
        
        testedAdapter = new OnlineSpeedCameraRecordUploadAdapter();
        testedAdapter.setVehicleSpeedProcessingService(port);
    }

    @Test
    void givenValidSpeedCameraRecordJsonStr_whenUploadViaAdapter_thenAddSpeedDataViaPortSuccess() throws Exception {
        
        String speedDataJsonStr = "{\"registrationPlateNo\" : \"JK7N87\", \"speed\" : 55.0, \"speedLimit\" : 80.0}";
        
        testedAdapter.uploadSpeedCameraJSONObjectStr(speedDataJsonStr);
        
        VehicleSpeedData data = new VehicleSpeedData("JK7N87", 55.0F, 80.0F);
        verify(port).addSpeedData(data);
    }
    
    @Test
    void givenInvalidSpeedCameraRecordJsonStr_whenUploadViaAdapter_thenExceptionThrown() {
        
        String invalidSpeedDataJsonStr = "{onPlateNo\" : \"JK7N87\", \"speed\" : 55.0 \"speedLimit\" : 80.0}";
        
        Executable executable = () -> testedAdapter.uploadSpeedCameraJSONObjectStr(invalidSpeedDataJsonStr);
        
        assertThrows(Exception.class, executable);
    }
}
