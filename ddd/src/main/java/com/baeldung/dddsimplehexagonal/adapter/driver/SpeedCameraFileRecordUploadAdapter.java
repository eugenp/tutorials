package com.baeldung.dddsimplehexagonal.adapter.driver;

import com.baeldung.dddsimplehexagonal.domain.VehicleSpeedData;
import com.baeldung.dddsimplehexagonal.domain.port.incoming.SpeedDataIncomingPort;
import com.baeldung.dddsimplehexagonal.external.driving.SpeedCameraFileRecordUploadInterface;

public class SpeedCameraFileRecordUploadAdapter implements SpeedCameraFileRecordUploadInterface {

    private SpeedDataIncomingPort incomingPort;

    @Override
    public void uploadSpeedCameraFileRecord(String filerecord) throws Exception {
        
        String[] stringParts = filerecord.split(FILE_RECORD_DELIMITER);
        String registrationPlateNo = stringParts[0];
        float speed = Float.parseFloat(stringParts[1]);
        float speedLimit = Float.parseFloat(stringParts[2]);
        
        VehicleSpeedData vehicleSpeedData = new VehicleSpeedData(registrationPlateNo, speed, speedLimit);
        incomingPort.addSpeedData(vehicleSpeedData);
    }

    public void setIncomingPort(SpeedDataIncomingPort incomingPort) {
        this.incomingPort = incomingPort;
    }
}
