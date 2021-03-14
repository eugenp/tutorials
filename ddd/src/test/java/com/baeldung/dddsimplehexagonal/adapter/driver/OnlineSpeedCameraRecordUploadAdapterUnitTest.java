package com.baeldung.dddsimplehexagonal.adapter.driver;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import com.baeldung.dddsimplehexagonal.adapter.driver.exception.DriverAdapterRuntimeException;
import com.baeldung.dddsimplehexagonal.domain.port.incoming.IncomingSpeedDataDTO;
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
    void givenValidSpeedCameraRecordJsonStr_whenUploadViaAdapter_thenAddSpeedDataViaPortSuccess() {
        
        String speedDataJsonStr = "{\"registrationPlateNo\" : \"JK7N87\", \"speed\" : 55.0, \"speedLimit\" : 80.0}";
        
        testedAdapter.uploadSpeedCameraJSONObjectStr(speedDataJsonStr);
        
        IncomingSpeedDataDTO dataDTO = new IncomingSpeedDataDTO("JK7N87", 55.0F, 80.0F);
        verify(port).addSpeedData(dataDTO);
    }
    
    @Test
    void givenInvalidSpeedCameraRecordJsonStr_whenUploadViaAdapter_thenRuntimeExceptionThrown() {
        
        String invalidSpeedDataJsonStr = "{onPlateNo\" : \"JK7N87\", \"speed\" : 55.0 \"speedLimit\" : 80.0}";
        
        Executable executable = () -> testedAdapter.uploadSpeedCameraJSONObjectStr(invalidSpeedDataJsonStr);
        
        assertThrows(DriverAdapterRuntimeException.class, executable);
    }
}
