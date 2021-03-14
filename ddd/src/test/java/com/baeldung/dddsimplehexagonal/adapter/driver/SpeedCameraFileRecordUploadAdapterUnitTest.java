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
    void givenValidSpeedCameraFileRecordStr_whenUploadViaAdapter_thenAddSpeedDataViaPortSuccess() {
        
        String speedFileRecordStr = "JK7N87:55.0:80.0";
        
        testedAdapter.uploadSpeedCameraFileRecord(speedFileRecordStr);
        
        IncomingSpeedDataDTO dataDTO = new IncomingSpeedDataDTO("JK7N87", 55.0F, 80.0F);
        verify(port).addSpeedData(dataDTO);
    }
    
    @Test
    void givenInvalidSpeedCameraFileRecordStr_whenUploadViaAdapter_thenRuntimeExceptionThrown() {
        
        String speedFileRecordStr = "{onPlateNo\" : \"JK7N87\", \"speed\" : 55.0 \"speedLimit\" : 80.0}";
        
        Executable executable = () -> testedAdapter.uploadSpeedCameraFileRecord(speedFileRecordStr);
        
        assertThrows(DriverAdapterRuntimeException.class, executable);
    }
    
    @Test
    void givenInvalidSpeedItemInFileRecordStr_whenUploadViaAdapter_thenRuntimeExceptionThrown() {
        
        String speedFileRecordStr = "JK7N87:blabla:80.0";
        
        Executable executable = () -> testedAdapter.uploadSpeedCameraFileRecord(speedFileRecordStr);
        
        assertThrows(DriverAdapterRuntimeException.class, executable);
    }

    @Test
    void givenInvalidSpeedLimitItemInFileRecordStr_whenUploadViaAdapter_thenRuntimeExceptionThrown() {
        
        String speedFileRecordStr = "JK7N87:43.0:haha";
        
        Executable executable = () -> testedAdapter.uploadSpeedCameraFileRecord(speedFileRecordStr);
        
        assertThrows(DriverAdapterRuntimeException.class, executable);
    }
}
