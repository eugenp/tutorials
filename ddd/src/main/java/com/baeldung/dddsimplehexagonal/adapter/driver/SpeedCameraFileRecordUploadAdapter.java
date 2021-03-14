package com.baeldung.dddsimplehexagonal.adapter.driver;

import com.baeldung.dddsimplehexagonal.adapter.driver.exception.DriverAdapterRuntimeException;
import com.baeldung.dddsimplehexagonal.domain.port.incoming.IncomingSpeedDataDTO;
import com.baeldung.dddsimplehexagonal.domain.port.incoming.SpeedDataIncomingPort;
import com.baeldung.dddsimplehexagonal.external.driving.SpeedCameraFileRecordUploadInterface;

public class SpeedCameraFileRecordUploadAdapter implements SpeedCameraFileRecordUploadInterface {

    private SpeedDataIncomingPort incomingPort;

    @Override
    public void uploadSpeedCameraFileRecord(String filerecord) {
        
        String[] stringParts = filerecord.split(FILE_RECORD_DELIMITER);
        if (stringParts.length != 3) {
            throw new DriverAdapterRuntimeException(
              "Failed to split the items out of the given file record: " + filerecord);
        }

        String registrationPlateNo = stringParts[0];
        
        float speed;
        try {
            speed = Float.parseFloat(stringParts[1]);
        } catch (NumberFormatException nfe) {
            throw new DriverAdapterRuntimeException(
              "Can't parse speed from file record item: " + stringParts[1], nfe);
        }
        
        float speedLimit;
        try {
            speedLimit = Float.parseFloat(stringParts[2]);
        } catch (NumberFormatException nfe) {
            throw new DriverAdapterRuntimeException(
              "Can't parse speedLimit from file record item: " + stringParts[2], nfe);
        }
        
        IncomingSpeedDataDTO incomingSpeedDataDTO = new IncomingSpeedDataDTO(registrationPlateNo, speed, speedLimit);
        incomingPort.addSpeedData(incomingSpeedDataDTO);
    }

    public void setIncomingPort(SpeedDataIncomingPort incomingPort) {
        this.incomingPort = incomingPort;
    }
}
