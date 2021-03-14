package com.baeldung.dddsimplehexagonal.adapter.driven;

import com.baeldung.dddsimplehexagonal.adapter.driven.exception.DrivenAdapterRuntimeException;
import com.baeldung.dddsimplehexagonal.domain.port.outgoing.OutgoingSpeedDataDTO;
import com.baeldung.dddsimplehexagonal.domain.port.outgoing.SpeedingOffenceOutgoingPort;
import com.baeldung.dddsimplehexagonal.external.driven.OnlineAutoFineIssuingInterface;
import com.baeldung.dddsimplehexagonal.external.driven.SpeedingFineJsonObj;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class OnlineAutoFineIssuingAdapter implements SpeedingOffenceOutgoingPort {
    
    private OnlineAutoFineIssuingInterface onlineFineIssuingSystem;

    @Override
    public void addSpeedingOffenseData(OutgoingSpeedDataDTO outgoingSpeedDataDTO) {
        
        String registrationPlateNo = outgoingSpeedDataDTO.getRegistrationPlateNo();
        float speed = outgoingSpeedDataDTO.getSpeed();
        float speedLimit = outgoingSpeedDataDTO.getSpeedLimit();
        
        if (registrationPlateNo == null) {
            throw new DrivenAdapterRuntimeException(
              "registrationPlateNo field is null in given outgoingDTO object");
        }        
        if (speed < 0) {
            throw new DrivenAdapterRuntimeException("speed in given outgoingDTO object can't be negative");
        }
        if (speedLimit < 0) {
            throw new DrivenAdapterRuntimeException("speedLimit in given outgoingDTO object can't be negative");
        }
        
        SpeedingFineJsonObj fineJsonObj = new SpeedingFineJsonObj(
          registrationPlateNo, speed, speedLimit);
        
        ObjectMapper mapper = new ObjectMapper();
        String fineJsonStr = null;
        try {
            fineJsonStr = mapper.writeValueAsString(fineJsonObj);
        } catch (JsonProcessingException jpe) {
            throw new DrivenAdapterRuntimeException("Can't serialize Speeding Fine JSON obj into string. "
              + "Registration Plate No: " + fineJsonObj.getRegistrationPlateNo() + ", "
              + "Speed: " + fineJsonObj.getSpeed() + ", "
              + "Speed Limit: " + fineJsonObj.getSpeedLimit() + ".",
              jpe);
        }
        
        onlineFineIssuingSystem.sendAutoFineIssuingJsonStr(fineJsonStr);
    }
    
    public void setOnlineFineIssuingSystem(OnlineAutoFineIssuingInterface onlineFineIssuingSystem) {
        this.onlineFineIssuingSystem = onlineFineIssuingSystem;
    }
}
