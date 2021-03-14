package com.baeldung.dddsimplehexagonal.adapter.driver;

import com.baeldung.dddsimplehexagonal.adapter.driver.exception.DriverAdapterRuntimeException;
import com.baeldung.dddsimplehexagonal.domain.port.incoming.IncomingSpeedDataDTO;
import com.baeldung.dddsimplehexagonal.domain.port.incoming.SpeedDataIncomingPort;
import com.baeldung.dddsimplehexagonal.external.driving.OnlineSpeedCameraJSONObjectUploadInterface;
import com.baeldung.dddsimplehexagonal.external.driving.OnlineSpeedCameraJsonObj;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class OnlineSpeedCameraRecordUploadAdapter implements OnlineSpeedCameraJSONObjectUploadInterface {
    
    private SpeedDataIncomingPort vehicleSpeedProcessingPort;

    @Override
    public void uploadSpeedCameraJSONObjectStr(String jsonObjectStr) {
        
        OnlineSpeedCameraJsonObj jsonObj = null;
        try {
            jsonObj = new ObjectMapper().readValue(jsonObjectStr, OnlineSpeedCameraJsonObj.class);
        } catch (JsonMappingException jme) {
            throw new DriverAdapterRuntimeException("Can't deserialize JSON Obj string " 
              + jsonObjectStr, jme);
        } catch (JsonProcessingException jpe) {
            throw new DriverAdapterRuntimeException("Can't deserialize JSON Obj string "
              + jsonObjectStr, jpe);
        }
        
        IncomingSpeedDataDTO incomingSpeedDataDto =
          new IncomingSpeedDataDTO(jsonObj.getRegistrationPlateNo(), jsonObj.getSpeed(), jsonObj.getSpeedLimit());
        vehicleSpeedProcessingPort.addSpeedData(incomingSpeedDataDto);
    }

    public void setVehicleSpeedProcessingService(SpeedDataIncomingPort vehicleSpeedProcessingService) {
        this.vehicleSpeedProcessingPort = vehicleSpeedProcessingService;
    }
}
