package com.baeldung.dddsimplehexagonal.adapter.driver;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import com.baeldung.dddsimplehexagonal.domain.VehicleSpeedData;
import com.baeldung.dddsimplehexagonal.domain.port.incoming.SpeedDataIncomingPort;

class SpeedCameraFileRecordUploadAdapterUnitTest {
    
    private SpeedCameraFileRecordUploadAdapter testedAdapter;
    private SpeedDataIncomingPort port;

    @BeforeEach
    void setUp() throws Exception {
        port = mock(SpeedDataIncomingPort.class);
        
        testedAdapter = new SpeedCameraFileRecordUploadAdapter();
        testedAdapter.setIncomingPort(port);
    }
    
    @Test
    void givenValidSpeedCameraFileRecordStr_whenUploadViaAdapter_thenAddSpeedDataViaPortSuccess() throws Exception {
        
        String validSpeedFileRecordStr = "JK7N87:55.0:80.0";
        
        testedAdapter.uploadSpeedCameraFileRecord(validSpeedFileRecordStr);
        
        VehicleSpeedData data = new VehicleSpeedData("JK7N87", 55.0F, 80.0F);
        verify(port).addSpeedData(data);
    }
    
    @Test
    void givenInvalidSpeedCameraFileRecordStr_whenUploadViaAdapter_thenExceptionThrown() {
        
        String invalidSpeedFileRecordStr = "{onPlateNo\" : \"JK7N87\", \"speed\" : 55.0 \"speedLimit\" : 80.0}";
        
        Executable executable = () -> testedAdapter.uploadSpeedCameraFileRecord(invalidSpeedFileRecordStr);
        
        assertThrows(Exception.class, executable);
    }
}
