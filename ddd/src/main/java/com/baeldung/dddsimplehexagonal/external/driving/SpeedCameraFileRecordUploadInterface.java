package com.baeldung.dddsimplehexagonal.external.driving;

public interface SpeedCameraFileRecordUploadInterface {
    
    static final String FILE_RECORD_DELIMITER = ":";
    
    void uploadSpeedCameraFileRecord(String filerecord);
}
