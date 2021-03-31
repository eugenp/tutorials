package com.baeldung.dddsimplehexagonal.adapter.driver;

import com.baeldung.dddsimplehexagonal.domain.VehicleSpeedData;
import com.baeldung.dddsimplehexagonal.domain.port.incoming.SpeedDataIncomingPort;
import com.baeldung.dddsimplehexagonal.external.driving.OnlineSpeedCameraJSONObjectUploadInterface;
import com.fasterxml.jackson.databind.ObjectMapper;

public class OnlineSpeedCameraRecordUploadAdapter implements OnlineSpeedCameraJSONObjectUploadInterface {
    
    private SpeedDataIncomingPort vehicleSpeedProcessingPort;

    @Override
    public void uploadSpeedCameraJSONObjectStr(String jsonObjectStr) throws Exception {
        
        VehicleSpeedData vehicleSpeedData = new ObjectMapper().readValue(jsonObjectStr, VehicleSpeedData.class);
        vehicleSpeedProcessingPort.addSpeedData(vehicleSpeedData);
    }

    public void setVehicleSpeedProcessingService(SpeedDataIncomingPort vehicleSpeedProcessingService) {
        this.vehicleSpeedProcessingPort = vehicleSpeedProcessingService;
    }
}
